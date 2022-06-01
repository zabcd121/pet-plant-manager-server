package domain.service;

import domain.model.Plant;
import domain.repository.PlantRepository;

import java.util.*;

public class PlantRecommendService {
    private final PlantRepository plantRepo;

    private static class Entry implements Comparable<Entry>{
        private Plant plant;
        private double similar;

        public Entry(Plant plant, double similar) {
            this.plant = plant;
            this.similar = similar;
        }

        public Plant getPlant() {
            return plant;
        }

        public double getSimilar() {
            return similar;
        }

        @Override
        public int compareTo(Entry o) {
            if(similar==o.similar){
                return 0;
            }else if(similar>o.similar){
                return 1;
            }else{
                return -1;
            }
        }
    }


    public PlantRecommendService(PlantRepository plantRepo) {
        this.plantRepo = plantRepo;
    }

    public List<Plant> recommend(float lightDemand, int humidity, int growthTp,
                           int growthSpeed, int mngLevel) {

        List<Plant> plantList = plantRepo.findByOption();
        PriorityQueue<Entry> topS = new PriorityQueue<>(Collections.reverseOrder());
        List<Plant> res = new ArrayList<>();

        for(Plant plant : plantList){
            double similarity = plant.calculateSimilarity(
                    lightDemand, humidity, growthTp,
                    growthSpeed, mngLevel
            );

            topS.add(new Entry(plant, similarity));
        }

        for(int i=0; i<3; i++){
            res.add(topS.poll().getPlant());
        }

        return res;
    }
}
