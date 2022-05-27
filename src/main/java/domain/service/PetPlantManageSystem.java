package domain.service;

import domain.model.PetPlant;
import domain.repository.PetPlantRepository;

import java.time.LocalDate;

public class PetPlantManageSystem {
    private final PetPlantRepository petPlantRepo;

    public PetPlantManageSystem(PetPlantRepository petPlantRepo){
        this.petPlantRepo = petPlantRepo;
    }

    public long create(long plantId, long userId, String petName, LocalDate firstMetDay /*, Byte[] petImg */){
        //현재 사용자의 반려식물 중에 중복되는 이름이 있을 경우 생성 불가
        if(isExisitingPetName(petName)){
            throw new IllegalArgumentException("중복되는 반려식물 이름입니다.");
        }

        PetPlant petPlant = PetPlant.builder(plantId, userId, petName, firstMetDay).build();
        long petPlantID = petPlantRepo.save(petPlant);

        return petPlantID;
    }


    public PetPlant updateName(PetPlant pet){
        //현재 사용자의 반려식물 중에 중복되는 이름이 있을 경우 변경 불가
//        if(isExisitingPetName(pet.getPetName())){
//            throw new IllegalArgumentException("중복되는 반려식물 이름입니다.");
//        }

        return pet;
    }


    private boolean isExisitingPetName(String petName){
//        if(petPlantRepo.findBy(petName) == null){
//            return false;
//        }

        return true;
    }


}
