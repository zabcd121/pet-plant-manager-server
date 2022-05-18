package controller;

import domain.repository.AccountRepository;
import infra.network.Request;
import infra.network.Response;

public class MainController {
    private AccountController accController;

    public MainController(AccountRepository accRepo) {
        accController = new AccountController(accRepo);
    }

    public Response handle(Request req){
        String firstLevel = URLParser.parseURLByLevel(req.url, 1);

        switch(firstLevel){
            case "account":{
                System.out.println("firstLevel = " + firstLevel);
                return accController.handle(req);
            }
        }
        return null;
    }
}
