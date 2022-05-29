package infra.network;

import domain.model.PetPlant;
import dto.ModelMapper;
import dto.PetPlantDTO;
import infra.database.repository.RDBAccountRepository;
import infra.database.repository.RDBPetPlantRepository;
import infra.database.repository.RDBPlantRepository;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Listener l = new Listener(new RDBAccountRepository(), new RDBPlantRepository(), new RDBPetPlantRepository());
        l.run();

//        RDBPetPlantRepository p = new RDBPetPlantRepository();
//
//        try(FileOutputStream fis = new FileOutputStream(new File("./res.png"))) {
//            PetPlant x = p.findByID(11);
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
    }
}
