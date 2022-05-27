package controller;

import domain.repository.AccountRepository;
import domain.repository.PlantRepository;
import infra.network.Request;
import infra.network.Response;

public class MainController {
    private final AccountController accController;
    private final PlantController plantController;

    public MainController(AccountRepository accRepo, PlantRepository plantRepo) {
        accController = new AccountController(accRepo);
        plantController = new PlantController(plantRepo);
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
        }
        return null;
    }
}
