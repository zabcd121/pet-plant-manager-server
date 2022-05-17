package controller;

import application.AccountAppService;
import domain.repository.AccountRepository;
import dto.AccountDTO;
import infra.network.Request;

public class AccountController {
    private AccountAppService appService;

    public AccountController(AccountRepository accRepo) {
        this.appService = new AccountAppService(accRepo);
    }

    public void handle(Request req){
        String secondLevel = URLParser.parseURLByLevel(req.url, 2);

        switch(secondLevel){
            case "login":{
                appService.login((AccountDTO) req.data.get("accountDTO"));
                break;
            }
            case "logout":{
                System.out.println("logout");
                break;
            }
            case "join":{
                break;
            }
            case "leave":{
                break;
            }
        }
    }
}
