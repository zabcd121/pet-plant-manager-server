package application;

import domain.model.Account;
import domain.model.PetPlant;
import domain.model.Watering;
import domain.repository.AccountRepository;
import domain.repository.PetPlantRepository;
import domain.repository.WateringRepository;
import domain.service.PetPlantManageSystem;
import dto.ModelMapper;
import dto.PetPlantDTO;
import dto.WateringDTO;
import infra.database.option.account.TokenOption;

import java.util.ArrayList;
import java.util.List;

public class PetPlantAppService {
    private final PetPlantRepository petPlantRepo;
    private final AccountRepository accRepo;
    private final PetPlantManageSystem petPlantManageSystem;

    public PetPlantAppService(PetPlantRepository petPlantRepo, AccountRepository accRepo, WateringRepository wateringRepo){
        this.petPlantRepo = petPlantRepo;
        this.accRepo = accRepo;

        petPlantManageSystem = new PetPlantManageSystem(petPlantRepo, wateringRepo);
    }

    public PetPlantDTO createPetPlant(PetPlantDTO dto) throws IllegalArgumentException{

        long petPlantID = petPlantManageSystem.create(
                dto.getPlantID(), dto.getUserID(), dto.getPetName(), dto.getFirstMetDay(), dto.getPetImg()
        );

        dto.setPk(petPlantID);

        if(petPlantID>0){
            return dto;
        }else{
            return null;
        }
    }

    public List<PetPlantDTO> retrievePetPlant(String token){
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

        PetPlant petPlant = petPlantManageSystem.updatePetPlant(dto.getPk(), dto.getUserID(), dto.getPetName());

        return ModelMapper.<PetPlant, PetPlantDTO>modelToDTO(petPlant, PetPlantDTO.class);
    }

    public void deletePetPlant(PetPlantDTO dto) throws IllegalArgumentException{
        petPlantManageSystem.deletePetPlant(dto.getPk(), dto.getUserID());
    }

    public List<WateringDTO> retrieveWateringHistory(PetPlantDTO dto){
        List<Watering> wateringList = petPlantManageSystem.retrieveWateringHistory(dto.getPk());
        List<WateringDTO> resList = new ArrayList<>();

        for(Watering watering : wateringList){
            resList.add(
                    ModelMapper.<Watering, WateringDTO>modelToDTO(watering, WateringDTO.class)
            );
        }

        return resList;
    }

    public WateringDTO createWatering(WateringDTO dto){
        Watering watering = petPlantManageSystem.createWatering(dto.getPetPlantPK(), dto.getWateringDay());
        return ModelMapper.modelToDTO(watering, WateringDTO.class);
    }
}
