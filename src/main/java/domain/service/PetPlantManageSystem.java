package domain.service;

import domain.model.PetPlant;
import domain.model.Watering;
import domain.repository.PetPlantRepository;
import domain.repository.WateringRepository;
import infra.database.option.petPlant.NameOption;
import infra.database.option.petPlant.UserPKOption;
import infra.database.option.watering.DateOption;
import infra.database.option.watering.MonthDateOption;
import infra.database.option.watering.PetPlantPKOption;

import java.time.LocalDate;
import java.util.List;

public class PetPlantManageSystem {
    private final PetPlantRepository petPlantRepo;
    private final WateringRepository wateringRepo;

    public PetPlantManageSystem(PetPlantRepository petPlantRepo, WateringRepository wateringRepo){
        this.petPlantRepo = petPlantRepo;
        this.wateringRepo = wateringRepo;
    }

    public long create(long plantId, long userId, String petName, LocalDate firstMetDay , byte[] petImg ) throws IllegalArgumentException{
        //현재 사용자의 반려식물 중에 중복되는 이름이 있을 경우 생성 불가
        if(isExisitingPetName(userId, petName)){
            throw new IllegalArgumentException("중복되는 반려식물 이름입니다.");
        }

        PetPlant petPlant = PetPlant.builder(plantId, userId, petName, firstMetDay).petImg(petImg).build();
        long petPlantID = petPlantRepo.save(petPlant);

        return petPlantID;
    }

    public List<PetPlant> retrievePetPlant(long userID){
       return petPlantRepo.findByOption(new UserPKOption(userID));
    }


    public PetPlant updatePetPlant(long petPlantID, long userID,
                                   String changeName, byte[] changeImg) throws IllegalArgumentException{
        PetPlant petPlant = petPlantRepo.findByID(petPlantID);

        if(petPlant==null){
            throw new IllegalArgumentException("존재하지 않는 PK 입니다.");
        }

        if(!petPlant.checkOwner(userID)){
            throw new IllegalArgumentException("나의 반려식물이 아닙니다.");
        }

        if(changeName!=null){
            //현재 사용자의 반려식물 중에 중복되는 이름이 있을 경우 변경 불가
            System.out.println("changeName = " + changeName);
            if(isExisitingPetName(userID, changeName)){
                throw new IllegalArgumentException("중복되는 반려식물 이름입니다.");
            }

            petPlant.setPetName(changeName);
        }

        if(changeImg!=null || changeImg.length==0){
            petPlant.setPetImg(changeImg);
        }

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

    public List<Watering> retrieveWateringHistory(long petPlantID){
        return wateringRepo.findByOption(new PetPlantPKOption(petPlantID));
    }

    public List<Watering> retrieveWateringBy(long userID, LocalDate date) {
        List<Watering> list = wateringRepo.findByOption(
                new infra.database.option.watering.UserPKOption(userID),
                new MonthDateOption(date)
        );

        return list;
    }

    public Watering createWatering(long petPlantID, LocalDate wateringDay, long userID){
        List<Watering> list = wateringRepo.findByOption(
                new DateOption(wateringDay), new PetPlantPKOption(petPlantID)
        );

        if(list.size()>0){
            throw new IllegalArgumentException("해당날짜에 물을 이미 줬습니다!");
        }

        long pk = wateringRepo.save(
                Watering.builder().petPlantPK(petPlantID).wateringDay(wateringDay).userPK(userID).build()
        );

        return wateringRepo.findByID(pk);
    }

    public void deleteWatering(long wateringPK){
        Watering watering = wateringRepo.findByID(wateringPK);

        if(watering==null){
            throw new IllegalArgumentException("존재하지 않는 PK 입니다.");
        }else{
            wateringRepo.remove(watering);
        }
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
