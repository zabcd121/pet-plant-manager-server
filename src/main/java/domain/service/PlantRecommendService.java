package domain.service;

import domain.model.Plant;
import domain.repository.PlantRepository;

import java.util.List;

public class PlantRecommendService {
    private final PlantRepository plantRepo;


    public PlantRecommendService(PlantRepository plantRepo) {
        this.plantRepo = plantRepo;
    }

    public Plant recommend(float lightDemand, int humidity, int growthTp,
                           int growthSpeed, int mngLevel, float clCode) {

        double maxSimilarity = -1;
        Plant recommendPlant = null;
        List<Plant> plantList = plantRepo.findByOption();

        for(Plant plant : plantList){
            double similarity = plant.calculateSimilarity(
                    lightDemand, humidity, growthTp,
                    growthSpeed, mngLevel, clCode
            );

            maxSimilarity = Math.max(maxSimilarity, similarity);

            if(maxSimilarity==similarity){
                recommendPlant = plant;
            }
        }

        return recommendPlant;
    }
}
