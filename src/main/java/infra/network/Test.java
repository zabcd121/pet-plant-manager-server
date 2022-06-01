package infra.network;

import application.PetPlantAppService;
import dto.WateringDTO;
import infra.database.repository.RDBAccountRepository;
import infra.database.repository.RDBPetPlantRepository;
import infra.database.repository.RDBPlantRepository;
import infra.database.repository.RDBWateringRepository;

import javax.swing.text.DateFormatter;
import domain.model.Diary;
import domain.model.Notice;
import infra.database.option.diary.UserPKOption;
import infra.database.repository.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Listener l = new Listener(new RDBAccountRepository(), new RDBPlantRepository(), new RDBPetPlantRepository(), new RDBWateringRepository(), new RDBNoticeRepository(), new RDBDiaryRepository());
        l.run();

//        Listener l = new Listener(new RDBAccountRepository(), new RDBPlantRepository(), new RDBPetPlantRepository(), new RDBWateringRepository(), new RDBNoticeRepository(), new RDBDiaryRepository());
//
//        l.run();

<<<<<<< HEAD
=======
        RDBDiaryRepository p = new RDBDiaryRepository();

        p.remove(Diary.builder().pk(5).build());
//        p.save(diary);

//            byte[] arr = {0x0001, 0x0002};
//            p.save(
//                    Diary.builder().pk(0).userPK(8).petPlantPK(37).title("new").content("내용12").diaryImg(arr).date(LocalDate.now()).build()
//
//            );



//        RDBNoticeRepository n = new RDBNoticeRepository();
//        Notice notice = Notice.builder().pk(0).targetAccId(1).targetPetId(27).targetPetName("찐이야").content("ㅉㄴㅉㄴㅉㄴ").noticedTime(LocalDate.now()).build();
//        notice.toString();
//        n.findByID(1);
>>>>>>> 59a997d0b3368cfc937e6e16da6f5b7503be4571

//        RDBPetPlantRepository p = new RDBPetPlantRepository();
//
//        PetPlant a = p.findByID(30);
//
//        for(byte b : a.getPetImg()){
//            System.out.println("b = " + b);
//        }

//
//        try(FileInputStream fis = new FileInputStream(new File("./img.png"))) {
//            byte[] arr = fis.readAllBytes();
//            p.save(
//                    PetPlant.builder(225, 6, "풍호", LocalDate.now()).petImg(
//                            arr
//                    ).build()
//            );
//            p.save(
//                    PetPlant.builder(225, 6, "는 떠다", LocalDate.now()).petImg(
//                            arr
//                    ).build()
//            );
//            p.save(
//                    PetPlant.builder(225, 6, "우릴 버", LocalDate.now()).petImg(
//                            arr
//                    ).build()
//            );
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        PlantAppService p = new PlantAppService(new RDBPlantRepository());
//        List<PlantDTO> res = p.recommendPlant(PlantDTO.builder().humidity(1).lightDemand(2).clCode(3).growthSpeed(3).growthTp(3).mngLevel(2).build());
//
//        for(PlantDTO d : res){
//            System.out.println("d = " + d);
//        }

//        RDBPlantRepository r = new RDBPlantRepository();
//        try(FileInputStream fis = new FileInputStream(new File("./purified.json"))) {
//            String str = new String(fis.readAllBytes());
//            JSONArray jsonArray = new JSONArray(str);
//
//                for(int i=0; i<jsonArray.length(); i++) {
//
//                    JSONObject obj = (JSONObject) jsonArray.get(i);
//                    try(FileInputStream imgFile = new FileInputStream(new File("./images/"+obj.getString("name")+".jpg"))){
//
//                        Plant p = Plant.builder()
//                                .pltName(obj.getString("name"))
//                                .lightDemand(obj.getFloat("lightDemand"))
//                                .humidity(obj.getInt("hdCode"))
//                                .growthTp(obj.getInt("grwhTpCode"))
//                                .growthSpeed(obj.getInt("grwtveCode"))
//                                .mngLevel(obj.getInt("managelevelCode"))
//                                .clCode(obj.getFloat("clCode"))
//                                .imgBytes(imgFile.readAllBytes())
//                                .build();
//
//                        r.save(p);
//                    }
//
//                }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        PetPlantController c = new PetPlantController(new RDBPetPlantRepository(), new RDBAccountRepository());
//
//        Request r = new Request(Request.Method.POST, "/petplant/mypetplant/");
//        r.data.put("petPlantDTO", PetPlantDTO.builder().plantID(22).userID(6).petName("김풍호").firstMetDay(LocalDate.now()).build());
//        Response res = c.handle(r);
//        System.out.println("res = " + res.statusCode);
//        RDBPetPlantRepository p = new RDBPetPlantRepository();
//
//        PetPlant a = p.findByID(30);
//
//        for(byte b : a.getPetImg()){
//            System.out.println("b = " + b);
//        }

//
//        try(FileInputStream fis = new FileInputStream(new File("./img.png"))) {
//            byte[] arr = fis.readAllBytes();
//            p.save(
//                    PetPlant.builder(225, 6, "풍호", LocalDate.now()).petImg(
//                            arr
//                    ).build()
//            );
//            p.save(
//                    PetPlant.builder(225, 6, "는 떠다", LocalDate.now()).petImg(
//                            arr
//                    ).build()
//            );
//            p.save(
//                    PetPlant.builder(225, 6, "우릴 버", LocalDate.now()).petImg(
//                            arr
//                    ).build()
//            );
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        PlantAppService p = new PlantAppService(new RDBPlantRepository());
//        List<PlantDTO> res = p.recommendPlant(PlantDTO.builder().humidity(1).lightDemand(2).clCode(3).growthSpeed(3).growthTp(3).mngLevel(2).build());
//
//        for(PlantDTO d : res){
//            System.out.println("d = " + d);
//        }

//        RDBPlantRepository r = new RDBPlantRepository();
//        try(FileInputStream fis = new FileInputStream(new File("./purified.json"))) {
//            String str = new String(fis.readAllBytes());
//            JSONArray jsonArray = new JSONArray(str);
//
//                for(int i=0; i<jsonArray.length(); i++) {
//
//                    JSONObject obj = (JSONObject) jsonArray.get(i);
//                    try(FileInputStream imgFile = new FileInputStream(new File("./images/"+obj.getString("name")+".jpg"))){
//
//                        Plant p = Plant.builder()
//                                .pltName(obj.getString("name"))
//                                .lightDemand(obj.getFloat("lightDemand"))
//                                .humidity(obj.getInt("hdCode"))
//                                .growthTp(obj.getInt("grwhTpCode"))
//                                .growthSpeed(obj.getInt("grwtveCode"))
//                                .mngLevel(obj.getInt("managelevelCode"))
//                                .clCode(obj.getFloat("clCode"))
//                                .imgBytes(imgFile.readAllBytes())
//                                .build();
//
//                        r.save(p);
//                    }
//
//                }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        PetPlantController c = new PetPlantController(new RDBPetPlantRepository(), new RDBAccountRepository());
//
//        Request r = new Request(Request.Method.POST, "/petplant/mypetplant/");
//        r.data.put("petPlantDTO", PetPlantDTO.builder().plantID(22).userID(6).petName("김풍호").firstMetDay(LocalDate.now()).build());
//        Response res = c.handle(r);
//        System.out.println("res = " + res.statusCode);
//        RDBPetPlantRepository p = new RDBPetPlantRepository();
//
//        p.save(PetPlant.builder(1, 1, "kim", LocalDate.now()).build());
//
//        RDBPetPlantRepository p = new RDBPetPlantRepository();
//        try(FileOutputStream fis = new FileOutputStream(new File("./res.png"))) {
//            PetPlant x = p.findByID(18);
//
//            PetPlantDTO dto = ModelMapper.modelToDTO(x, PetPlantDTO.class);
//
//            fis.write(dto.getPetImg());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        PlantRecommendService s = new PlantRecommendService(new RDBPlantRepository());
//        Plant p = s.recommend(2, 3, 1, 3, 3, 4);
//        System.out.println("p = " + p);


//
//        try(FileInputStream fos = new FileInputStream(new File("/Users/laon/IdeaProjects/pet_plant_manager_server/src/main/resources/vector_data.json"))) {
//            String str = new String(fos.readAllBytes());
//            JSONArray jsonArray = new JSONArray(str);
//
//            for(int i=0; i<jsonArray.length(); i++){
//                JSONObject obj = (JSONObject) jsonArray.get(i);
//                Plant p = Plant.builder()
//                        .pltName(obj.getString("name"))
//                        .lightDemand(obj.getFloat("lightDemand"))
//                        .humidity(obj.getInt("hdCode"))
//                        .growthTp(obj.getInt("grwhTpCode"))
//                        .growthSpeed(obj.getInt("grwtveCode"))
//                        .mngLevel(obj.getInt("managelevelCode"))
//                        .clCode(obj.getFloat("clCode"))
//                        .build();
//
//                r.save(p);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
    }}