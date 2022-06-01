package application;

import domain.model.*;
import domain.repository.*;
import dto.AccountDTO;
import dto.ModelMapper;
import dto.NoticeDTO;
import infra.database.option.account.PKOption;
import infra.database.option.account.TokenOption;
import infra.database.option.petPlant.UserPKOption;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class NoticeAppService {

    private static final String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String OPEN_WEATHER_USER_KEY = "9fe0e398a531127b970aa5ef1762a749";

    private AccountRepository accRepo;
    private PetPlantRepository petRepo;
    private PlantRepository plantRepo;
    private NoticeRepository noticeRepo;
    private WateringRepository wateringRepo;


    public NoticeAppService(AccountRepository accRepo, PetPlantRepository petRepo, PlantRepository plantRepo, NoticeRepository noticeRepo, WateringRepository wateringRepo) {
        this.accRepo = accRepo;
        this.petRepo = petRepo;
        this.plantRepo = plantRepo;
        this.noticeRepo = noticeRepo;
        this.wateringRepo = wateringRepo;
    }

    public List<NoticeDTO> createNotice(AccountDTO accDTO) { //아침 9시에 알림 보냄
        List<Notice> noticeList = new ArrayList<>();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        Notice notice = Notice.builder()
                .targetAccId(accDTO.getPk())
                .targetPetId(0)
                .noticedTime(LocalDate.now())
                .build();

        try {
            if (noticeRepo.findByOption(new infra.database.option.notice.UserPKOption(accDTO.getPk())).size()!=0) {
                return retrieveNotices(accDTO.getToken());
            }

            Weather weather = loadWeather(accDTO);
            long accPk = accDTO.getPk();

            List<PetPlant> petList = petRepo.findByOption(new UserPKOption(accPk));

            if (weather.isHugeTempRange()) {/*accPk, -1, "일교차가 심하니 식물들을 실내로 넣어주세요", new Date()*/
                notice.setContent(
                        "일교차가 심하니 식물들을 실내로 넣어주세요"
                );

                noticeList.add(notice);
            } else if (weather.isSunny()) {
                notice.setContent(
                        "오늘 날씨가 좋아요 식물들 햇빛을 보게 해주세요"
                );

                noticeList.add(notice);
            }

            for (PetPlant petPlant : petList) {
                Plant plant = plantRepo.findByID(petPlant.getPlantID());

                notice = Notice.builder()
                            .targetAccId(accPk)
                            .targetPetId(petPlant.getPk())
                            .targetPetName(petPlant.getPetName())
                            .noticedTime(LocalDate.now())
                            .build();

                //날씨 관련 알림
                if (plant.checkMaxTemp(weather.getMaxTemp())) {
                    notice.setContent(
                        "오늘 최고온도가 " + weather.getMaxTemp() + "℃ "
                                + "로 " + petPlant.getPetName() + "이가 위험하니 조심해주세요!"
                    );

                    noticeList.add(notice);
                } else if (plant.checkMinTemp(weather.getMinTemp())) {
                    notice.setContent(
                        "오늘 최고온도가 " + weather.getMinTemp() + "℃ " +
                                "로 " + petPlant.getPetName() + "이가 위험하니 조심해주세요!"
                    );

                    noticeList.add(notice);
                }

                List<Watering> wateringList = wateringRepo.findByOption();
                wateringList.sort(Collections.reverseOrder());
                Watering lastWatering = wateringList.get(0);

                //물주기 관련 알림
                if(plant.checkWateringCycle(
                        weather.getAverTemp(), weather.getHumidity(),
                        lastWatering.getWateredDays()
                )){
                    noticeList.add(
                            Notice.builder()
                                    .targetAccId(accPk)
                                    .targetPetId(petPlant.getPk())
                                    .targetPetName(petPlant.getPetName())
                                    .content(
                                            String.format(
                                                    "물준지 %d일 지났습니다. %s가 마르지 않았나요?",
                                                        lastWatering.getWateredDays(), petPlant.getPetName()
                                                    )
                                    )
                                    .noticedTime(LocalDate.now())
                                    .build()
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Notice n : noticeList) {
            noticeRepo.save(n);
            noticeDTOList.add(ModelMapper.<Notice, NoticeDTO>modelToDTO(n, NoticeDTO.class));
        }

        return noticeDTOList;
    }


    public List<NoticeDTO> retrieveNotices(String token) {
        Account acc = accRepo.findByOption(new TokenOption(token)).get(0);

        List<Notice> list = noticeRepo.findByOption(new UserPKOption(acc.getPk()));
        List<NoticeDTO> resList = new ArrayList<>();

        for (Notice notice : list) {
            resList.add(
                    ModelMapper.<Notice, NoticeDTO>modelToDTO(notice, NoticeDTO.class)
            );
        }

        return resList;
    }

    public void deleteNotice() throws IllegalArgumentException {

        noticeRepo.remove();

    }

    private static Weather loadWeather(AccountDTO accDTO) throws IOException, ParseException {
        URL url = new URL(OPEN_WEATHER_URL + "lat=" + accDTO.getX() + "&lon=" + accDTO.getY() + "&lang=kr&units=metric&appid=" + OPEN_WEATHER_USER_KEY);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = "";
        String response = "";

        while ((line = br.readLine()) != null) {
            response = response.concat(line);
        }
        System.out.println(response); //확인용

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(response);

        JSONArray jsonArr = (JSONArray) jsonObj.get("list");
        JSONObject totWeatherObj = (JSONObject) jsonArr.get(0);
        JSONObject tempObj = (JSONObject) totWeatherObj.get("temp");
        JSONArray weatherArr = (JSONArray) totWeatherObj.get("weather");
        JSONObject weatherObj = (JSONObject) weatherArr.get(0);

        Weather weather = Weather.builder()
                .humidity(
                        Integer.parseInt(totWeatherObj.get("humidity").toString()))
                .minTemp(
                        Float.parseFloat(tempObj.get("min").toString()))
                .maxTemp(
                        Float.parseFloat(tempObj.get("max").toString()))
                .weatherID(
                        Integer.parseInt(weatherObj.get("id").toString())
                ).build();

        return weather;
    }


}
