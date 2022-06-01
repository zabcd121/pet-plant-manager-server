package application;

import com.mysql.cj.xdevapi.JsonParser;
import domain.model.Account;
import domain.model.Notice;
import domain.model.PetPlant;
import domain.model.Plant;
import domain.repository.AccountRepository;
import domain.repository.NoticeRepository;
import domain.repository.PetPlantRepository;
import domain.repository.PlantRepository;
import domain.service.PetPlantManageSystem;
import dto.AccountDTO;
import dto.ModelMapper;
import dto.NoticeDTO;
import dto.PetPlantDTO;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class NoticeAppService {

    private static  final String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String OPEN_WEATHER_USER_KEY = "키 들어갈 자리";

    private AccountRepository accRepo;
    private PetPlantRepository petRepo;
    private PlantRepository plantRepo;
    private NoticeRepository noticeRepo;


    public NoticeAppService(AccountRepository accRepo, PetPlantRepository petRepo, PlantRepository plantRepo, NoticeRepository noticeRepo){
        this.accRepo = accRepo;
        this.petRepo = petRepo;
        this.plantRepo = plantRepo;
        this.noticeRepo = noticeRepo;
    }

    public List<NoticeDTO> createNotice(AccountDTO accDTO) { //아침 9시에 알림 보냄

        List<Notice> noticeList = new ArrayList<>();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        Notice notice;

        try {
            if(noticeRepo.findByOption(new PKOption(accDTO.getPk())) != null){
                return null;
            }
//            URL url = new URL(OPEN_WEATHER_URL + "lat=" + accDTO.getLat() + "&lon=" + accDTO.getLon() + "&lang=kr&units=metric&appid=" + OPEN_WEATHER_USER_KEY);
            URL url = new URL(OPEN_WEATHER_URL + "lat=36.1379262" +  "&lon=128.411519"  + "&lang=kr&units=metric&appid=" + OPEN_WEATHER_USER_KEY);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            String response = "";

            while((line = br.readLine()) != null){
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

            int humidity = Integer.parseInt(totWeatherObj.get("humidity").toString());
            float temp_min =  Float.parseFloat(tempObj.get("min").toString());
            float temp_max = Float.parseFloat(tempObj.get("max").toString());
            int weather_id = Integer.parseInt(weatherObj.get("id").toString());
            System.out.println("최저 기온: " + temp_min + " 최대 기온: " + temp_max + " 습도: " + humidity + " 날씨 상태: " + weather_id);


            long accPk = accDTO.getPk();

            List<PetPlant> pets = petRepo.findByOption(new PKOption(accPk));
            Iterator iter = pets.iterator();

            if((temp_max - temp_min) >= 15 ){/*accPk, -1, "일교차가 심하니 식물들을 실내로 넣어주세요", new Date()*/
                notice = Notice.builder()
                            .targetAccId(accPk)
                            .targetPetId(0)
                            .content("일교차가 심하니 식물들을 실내로 넣어주세요")
                            .noticedTime(LocalDate.now())
                            .build();

                noticeList.add(notice);
            }else if( weather_id == 800){
                notice = Notice.builder()
                        .targetAccId(accPk)
                        .targetPetId(0)
                        .content("오늘 날씨가 좋아요 식물들 햇빛을 보게 해주세요")
                        .noticedTime(LocalDate.now())
                        .build();
            }

            PetPlant pet;
            long plantID;
            Plant plant;
            int petGrowthTp;

            while(iter.hasNext()){

                pet = (PetPlant) iter.next();
                plantID = pet.getPlantID();
                plant = plantRepo.findByID(plantID);
                petGrowthTp = plant.getGrowthTp();

                if(temp_max >= petGrowthTp){
                    notice = Notice.builder()
                            .targetAccId(accPk)
                            .targetPetId(plantID)
                            .content("오늘 최고온도가 " + temp_max +"℃ " + "로 "  + pet.getPetName() + "이가 위험하니 조심해주세요!")
                            .noticedTime(LocalDate.now())
                            .build();

                    noticeList.add(notice);

                }else if(temp_min <= petGrowthTp){
                    notice = Notice.builder()
                            .targetAccId(accPk)
                            .targetPetId(plantID)
                            .content("오늘 최고온도가 " + temp_max +"℃ " + "로 "  + pet.getPetName() + "이가 위험하니 조심해주세요!")
                            .noticedTime(LocalDate.now())
                            .build();

                    noticeList.add(notice);
                }
            }

            for(Notice n : noticeList){
                noticeRepo.save(n);
                noticeDTOList.add(ModelMapper.<Notice, NoticeDTO>modelToDTO(n, NoticeDTO.class));
            }

            return noticeDTOList;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public List<NoticeDTO> retrieveNotices(String token){

        Account acc = accRepo.findByOption(new TokenOption(token)).get(0);

        List<Notice> list = noticeRepo.findByOption(new UserPKOption(acc.getPk()));
        List<NoticeDTO> resList = new ArrayList<>();

        for(Notice notice : list){
            resList.add(
                    ModelMapper.<Notice, NoticeDTO>modelToDTO(notice, NoticeDTO.class)
            );
        }

        return resList;
    }

    public void deleteNotice() throws IllegalArgumentException{

        noticeRepo.remove();

    }

}
