package controller;

import application.AccountAppService;
import domain.repository.AccountRepository;
import dto.AccountDTO;
import dto.MessageDTO;
import infra.network.Request;
import infra.network.Response;

public class AccountController {
    private AccountAppService appService;

    public AccountController(AccountRepository accRepo) {
        this.appService = new AccountAppService(accRepo);
    }

    public Response handle(Request req){
        String secondLevel = URLParser.parseURLByLevel(req.url, 2);
        Response res = null;

        switch(secondLevel){
            case "login":{
                AccountDTO resData = appService.login((AccountDTO) req.data.get("accountDTO"));

                if(resData!=null){
                    res = new Response(Response.StatusCode.SUCCESS);
                }else{
                    res = new Response(Response.StatusCode.FAIL);
                }
                res.data.put("accountDTO", resData);

                return res;
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

        return null;
    }
}
