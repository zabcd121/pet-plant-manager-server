package application;

import domain.repository.PetPlantRepository;

public class PetPlantAppService {

    private PetPlantRepository petPlantRepo;

    public PetPlantAppService(PetPlantRepository petPlantRepo){
        this.petPlantRepo = petPlantRepo;
    }


}
