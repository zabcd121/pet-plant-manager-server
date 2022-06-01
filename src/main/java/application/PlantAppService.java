package application;

import domain.model.Plant;
import domain.repository.PlantRepository;
import domain.service.PlantRecommendService;
import dto.ModelMapper;
import dto.PlantDTO;
import dto.WateringDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlantAppService {
    private final PlantRepository plantRepo;

    public PlantAppService(PlantRepository plantRepo) {
        this.plantRepo = plantRepo;
    }

    public List<PlantDTO> retrieveAll(){
        //TODO : 토큰 확인 필요

        List<Plant> plantList = plantRepo.findByOption();
        List<PlantDTO> res = new ArrayList<>();

        for(Plant p : plantList){
            res.add(
                    ModelMapper.<Plant, PlantDTO>modelToDTO(p, PlantDTO.class)
            );
        }

        if(res.size()==0){
            return null;
        }else{
            return res;
        }
    }

    public PlantDTO retrieveByID(PlantDTO dto){
        Plant plant = plantRepo.findByID(dto.getPk());

        if(plant==null){
            return null;
        }else{
            return ModelMapper.modelToDTO(plant, PlantDTO.class);
        }
    }

    public List<PlantDTO> recommendPlant(PlantDTO dto){
        PlantRecommendService plantRecommendService = new PlantRecommendService(plantRepo);

        List<Plant> recommendedPlants = plantRecommendService.recommend(
                dto.getLightDemand(), dto.getHumidity(), dto.getGrowthTp(),
                dto.getGrowthSpeed(), dto.getMngLevel(), dto.getClCode(), dto.getSeasonWaterCycle()
        );

        List<PlantDTO> res = new ArrayList<>();

        if(recommendedPlants.size()==0){
            return res;
        }else{
            for(Plant p : recommendedPlants){
                res.add(
                    ModelMapper.<Plant,PlantDTO>modelToDTO(p, PlantDTO.class)
                );
            }
        }

        return res;
    }

}
