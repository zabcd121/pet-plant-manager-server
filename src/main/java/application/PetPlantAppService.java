package application;

import domain.repository.PetPlantRepository;
import domain.service.PetPlantManageSystem;
import dto.PetPlantDTO;

public class PetPlantAppService {
    private final PetPlantRepository petPlantRepo;

    public PetPlantAppService(PetPlantRepository petPlantRepo){
        this.petPlantRepo = petPlantRepo;
    }

    public PetPlantDTO createPetPlant(PetPlantDTO dto){
        PetPlantManageSystem petPlantManageSystem = new PetPlantManageSystem(petPlantRepo);

        long petPlantID = petPlantManageSystem.create(
                dto.getPlantID(), dto.getUserID(), dto.getPetName(), dto.getFirstMetDay()
        );

        dto.setPk(petPlantID);

        if(petPlantID>0){
            return dto;
        }else{
            return null;
        }
    }

}
