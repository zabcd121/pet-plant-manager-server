package controller;

import infra.network.Request;

public class AccountController {
    public void handle(Request req){
        String secondLevel = URLParser.parseURLByLevel(req.url, 2);

        switch(secondLevel){
            case "login":{
                System.out.println("login");
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
