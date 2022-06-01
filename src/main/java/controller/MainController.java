package controller;

import domain.model.Notice;
import domain.repository.*;
import infra.network.Request;
import infra.network.Response;

public class MainController {
    private final AccountController accController;
    private final PlantController plantController;
    private final PetPlantController petPlantController;
    private final NoticeController noticeController;

    private final DiaryController diaryController;


    public MainController(AccountRepository accRepo, PlantRepository plantRepo, PetPlantRepository petPlantRepo, NoticeRepository noticeRepo, WateringRepository wateringRepo, DiaryRepository diaryRepo) {
        accController = new AccountController(accRepo);
        plantController = new PlantController(plantRepo);
        petPlantController = new PetPlantController(petPlantRepo, accRepo, wateringRepo);
        noticeController = new NoticeController(accRepo, petPlantRepo, plantRepo, noticeRepo);
        diaryController = new DiaryController(diaryRepo);
    }

    public Response handle(Request req){
        String firstLevel = URLParser.parseURLByLevel(req.url, 1);

        switch(firstLevel){
            case "account":{
                return accController.handle(req);
            }
            case "plant":{
                return plantController.handle(req);
            }
            case "petplant":{
                return petPlantController.handle(req);
            }
            case "notice":{
                return noticeController.handle(req);
            }
            case "diary":{
                return diaryController.handle(req);
            }
        }
        return null;
    }
}
