package domain.service;

import domain.model.PetPlant;
import domain.repository.PetPlantRepository;
import infra.database.option.petPlant.NameOption;
import infra.database.option.petPlant.UserPKOption;

import java.time.LocalDate;
import java.util.List;

public class PetPlantManageSystem {
    private final PetPlantRepository petPlantRepo;

    public PetPlantManageSystem(PetPlantRepository petPlantRepo){
        this.petPlantRepo = petPlantRepo;
    }

    public long create(long plantId, long userId, String petName, LocalDate firstMetDay /*, Byte[] petImg */) throws IllegalArgumentException{
        //현재 사용자의 반려식물 중에 중복되는 이름이 있을 경우 생성 불가
        if(isExisitingPetName(userId, petName)){
            throw new IllegalArgumentException("중복되는 반려식물 이름입니다.");
        }

        PetPlant petPlant = PetPlant.builder(plantId, userId, petName, firstMetDay).build();
        long petPlantID = petPlantRepo.save(petPlant);

        return petPlantID;
    }

    public List<PetPlant> retrievePetPlant(long userID){
       return petPlantRepo.findByOption(new UserPKOption(userID));
    }


    public PetPlant updatePetPlant(long petPlantID, long userID, String changeName) throws IllegalArgumentException{ //TODO : 이미지 변경 추가
        PetPlant petPlant = petPlantRepo.findByID(petPlantID);

        if(petPlant==null){
            throw new IllegalArgumentException("존재하지 않는 PK 입니다.");
        }

        if(petPlant.checkOwner(userID)){
            throw new IllegalArgumentException("나의 반려식물이 아닙니다.");
        }

        //현재 사용자의 반려식물 중에 중복되는 이름이 있을 경우 변경 불가
        if(isExisitingPetName(userID, changeName)){
            throw new IllegalArgumentException("중복되는 반려식물 이름입니다.");
        }

        petPlant.setPetName(changeName);

        petPlantRepo.save(petPlant);

        return petPlant;
    }

    public void deletePetPlant(long petPlantID, long userID){
        PetPlant petPlant = petPlantRepo.findByID(petPlantID);

        if(petPlant==null){
            throw new IllegalArgumentException("존재하지 않는 PK 입니다.");
        }

        if(petPlant.checkOwner(userID)){
            throw new IllegalArgumentException("나의 반려식물이 아닙니다.");
        }

        petPlantRepo.remove(petPlant);
    }

    private boolean isExisitingPetName(long userID, String petName){
        if(petPlantRepo.findByOption(
                new UserPKOption(userID),
                new NameOption(petName)
        ).size()==0){
            return false;
        }

        return true;
    }


}
