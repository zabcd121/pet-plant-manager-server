package controller;

import domain.repository.AccountRepository;
import infra.network.Request;

public class MainController {
    private AccountController accController;

    public MainController(AccountRepository accRepo) {
        accController = new AccountController(accRepo);
    }

    public void handle(Request req){
        String firstLevel = URLParser.parseURLByLevel(req.url, 1);

        switch(firstLevel){
            case "account":{
                System.out.println("firstLevel = " + firstLevel);
                accController.handle(req);
            }
        }
    }
}
