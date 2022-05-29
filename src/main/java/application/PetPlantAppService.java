package application;

import domain.model.Account;
import domain.model.PetPlant;
import domain.repository.AccountRepository;
import domain.repository.PetPlantRepository;
import domain.service.PetPlantManageSystem;
import dto.ModelMapper;
import dto.PetPlantDTO;
import infra.database.option.account.TokenOption;

import java.util.ArrayList;
import java.util.List;

public class PetPlantAppService {
    private final PetPlantRepository petPlantRepo;
    private final AccountRepository accRepo;

    public PetPlantAppService(PetPlantRepository petPlantRepo, AccountRepository accRepo){
        this.petPlantRepo = petPlantRepo;
        this.accRepo = accRepo;
    }

    public PetPlantDTO createPetPlant(PetPlantDTO dto) throws IllegalArgumentException{
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

    public List<PetPlantDTO> retrievePetPlant(String token){
        PetPlantManageSystem petPlantManageSystem = new PetPlantManageSystem(petPlantRepo);
        Account acc = accRepo.findByOption(new TokenOption(token)).get(0);

        List<PetPlant> list = petPlantManageSystem.retrievePetPlant(acc.getPk());
        List<PetPlantDTO> resList = new ArrayList<>();

        for(PetPlant plant : list){
            resList.add(
                    ModelMapper.<PetPlant, PetPlantDTO>modelToDTO(plant, PetPlantDTO.class)
            );
        }

        return resList;
    }

    public PetPlantDTO updatePetPlant(PetPlantDTO dto) throws IllegalArgumentException{
        PetPlantManageSystem petPlantManageSystem = new PetPlantManageSystem(petPlantRepo);

        PetPlant petPlant = petPlantManageSystem.updatePetPlant(dto.getPk(), dto.getUserID(), dto.getPetName());

        return ModelMapper.<PetPlant, PetPlantDTO>modelToDTO(petPlant, PetPlantDTO.class);
    }

    public void deletePetPlant(PetPlantDTO dto) throws IllegalArgumentException{
        PetPlantManageSystem petPlantManageSystem = new PetPlantManageSystem(petPlantRepo);
        petPlantManageSystem.deletePetPlant(dto.getPk(), dto.getUserID());
    }
}
