package controller;

import application.AccountAppService;
import domain.model.Account;
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

        switch(secondLevel){
            case "login":{
                return processLogin(req);
            }
            case "logout":{
                System.out.println("logout");
                break;
            }
            case "signup":{
                return processSignup(req);
            }
            case "leave":{
                break;
            }
        }

        return null;
    }

    private Response processLogin(Request req) {
        Response res = null;

        switch(req.method){
            case POST:{
                AccountDTO resData = appService.login((AccountDTO) req.data.get("accountDTO"));

                if(resData!=null){
                    res = new Response(Response.StatusCode.SUCCESS);
                }else{
                    res = new Response(Response.StatusCode.FAIL);
                }
                res.data.put("accountDTO", resData);
            }
        }

        return res;
    }

    private Response processSignup(Request req) {
        Response res = null;

        switch(req.method){
            case POST:{
                boolean isSignUp = appService.signup((AccountDTO) req.data.get("accountDTO"));

                if(isSignUp){
                    res = new Response(Response.StatusCode.SUCCESS);
                }else{
                    res = new Response(Response.StatusCode.FAIL);
                }
            }
        }

        return res;
    }

}
