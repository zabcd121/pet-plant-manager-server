package controller;

import application.PetPlantAppService;
import domain.repository.PetPlantRepository;
import infra.network.Request;
import infra.network.Response;

public class PetPlantController {
    private final PetPlantAppService petPlantAppService;

    public PetPlantController(PetPlantRepository petPlantRepo) {
        petPlantAppService = new PetPlantAppService(petPlantRepo);
    }

    public Response handle(Request req) {
        String secondLevel = URLParser.parseURLByLevel(req.url, 2);

        switch(secondLevel){
            case "create":{
                return processCreate(req);
            }
            case "all":{
            }
            case "update":{
            }
            case "delete":{
            }
        }

        return null;
    }

    private Response processCreate(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
            }
        }

        return res;
    }
}
