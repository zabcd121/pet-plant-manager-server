package application;

import domain.model.Plant;
import domain.repository.PlantRepository;
import domain.service.PlantRecommendService;
import dto.ModelMapper;
import dto.PlantDTO;

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

    public PlantDTO recommendPlant(PlantDTO dto){
        PlantRecommendService plantRecommendService = new PlantRecommendService(plantRepo);

        Plant recommendedPlant = plantRecommendService.recommend(
                dto.getLightDemand(), dto.getHumidity(), dto.getGrowthTp(),
                dto.getGrowthSpeed(), dto.getMngLevel(), dto.getClCode()
        );

        if(recommendedPlant==null){
            return null;
        }else{
            return ModelMapper.<Plant,PlantDTO>modelToDTO(recommendedPlant, PlantDTO.class);
        }
    }
}
