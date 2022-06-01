package controller;

import domain.model.Notice;
import domain.repository.AccountRepository;
import domain.repository.NoticeRepository;
import domain.repository.PetPlantRepository;
import domain.repository.PlantRepository;
import domain.repository.WateringRepository;
import infra.network.Request;
import infra.network.Response;

public class MainController {
    private final AccountController accController;
    private final PlantController plantController;
    private final PetPlantController petPlantController;
    private final NoticeController noticeController;


    public MainController(AccountRepository accRepo, PlantRepository plantRepo, PetPlantRepository petPlantRepo, NoticeRepository noticeRepo, WateringRepository wateringRepo) {
        accController = new AccountController(accRepo);
        plantController = new PlantController(plantRepo);
        petPlantController = new PetPlantController(petPlantRepo, accRepo, wateringRepo);
        noticeController = new NoticeController(accRepo, petPlantRepo, plantRepo, noticeRepo);

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
        }
        return null;
    }
}
