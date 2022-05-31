package controller;

import domain.repository.AccountRepository;
import domain.repository.PetPlantRepository;
import domain.repository.PlantRepository;
import domain.repository.WateringRepository;
import infra.network.Request;
import infra.network.Response;

public class MainController {
    private final AccountController accController;
    private final PlantController plantController;
    private final PetPlantController petPlantController;

    public MainController(AccountRepository accRepo, PlantRepository plantRepo, PetPlantRepository petPlantRepo, WateringRepository wateringRepo) {
        accController = new AccountController(accRepo);
        plantController = new PlantController(plantRepo);
        petPlantController = new PetPlantController(petPlantRepo, accRepo, wateringRepo);
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
        }
        return null;
    }
}
