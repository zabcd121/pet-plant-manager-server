package domain.service;

import domain.model.PetPlant;
import domain.repository.PetPlantRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class PetPlantManageSystem {
    private PetPlantRepository petPlantRepo;

    public PetPlantManageSystem(PetPlantRepository petPlantRepo){
        this.petPlantRepo = petPlantRepo;
    }

    public PetPlant create(int plantId, String petName, Date firstMetDay, Byte[] petImg){
        //현재 사용자의 반려식물 중에 중복되는 이름이 있을 경우 생성 불가
        if(isExisitingPetName(petName)){
            throw new IllegalArgumentException("중복되는 반려식물 이름입니다.");
        }
        return PetPlant.builder(plantId, petName, firstMetDay)
                .petImg(petImg)
                .build();
    }


    public PetPlant updateName(PetPlant pet){
        //현재 사용자의 반려식물 중에 중복되는 이름이 있을 경우 변경 불가
        if(isExisitingPetName(pet.getPetName())){
            throw new IllegalArgumentException("중복되는 반려식물 이름입니다.");
        }

        return pet;
    }


    private boolean isExisitingPetName(String petName){
        if(petPlantRepo.findBy(petName) == null){
            return false;
        }
        return true;
    }


}
