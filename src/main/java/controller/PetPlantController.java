package controller;

import application.PetPlantAppService;
import domain.model.PetPlant;
import domain.repository.AccountRepository;
import domain.repository.PetPlantRepository;
import dto.MessageDTO;
import dto.PetPlantDTO;
import infra.network.Request;
import infra.network.Response;

import java.util.List;

public class PetPlantController {
    private final PetPlantAppService petPlantAppService;

    public PetPlantController(PetPlantRepository petPlantRepo, AccountRepository accRepo) {
        petPlantAppService = new PetPlantAppService(petPlantRepo, accRepo);
    }

    public Response handle(Request req) {
        String secondLevel = URLParser.parseURLByLevel(req.url, 2);

        switch(secondLevel){
            case "mypetplant":{
                return processPetplant(req);
            }
            case "update":{
                return processUpdate(req);
            }
            case "delete":{
                return processDelete(req);
            }
        }

        return null;
    }

    private Response processPetplant(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                String errorMsg = null;
                PetPlantDTO petPlantDTO = null;

                try{
                    petPlantDTO = petPlantAppService.createPetPlant((PetPlantDTO) req.data.get("petPlantDTO"));
                }catch(IllegalArgumentException e){
                    errorMsg = e.getMessage();
                }finally {
                    if(petPlantDTO==null){
                        res = new Response(Response.StatusCode.FAIL);
                        res.data.put("messageDTO", new MessageDTO(errorMsg));
                    }else{
                        res = new Response(Response.StatusCode.SUCCESS);
                    }

                }


                res.data.put("petPlantDTO", petPlantDTO);
            }

            case GET:{
                List<PetPlantDTO> resData = null;

                resData = petPlantAppService.retrievePetPlant(req.token);

                if(resData.size()==0){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                }

                res.data.put("petPlantList", resData);
            }
        }

        return res;
    }

    private Response processUpdate(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                PetPlantDTO resData = null;
                MessageDTO errorMsg = null;

                try{
                    resData = petPlantAppService.updatePetPlant((PetPlantDTO) req.data.get("petPlantDTO"));
                    res = new Response(Response.StatusCode.SUCCESS);
                    res.data.put("petPlantDTO", resData);
                }catch (IllegalArgumentException e){
                    errorMsg = new MessageDTO(e.getMessage());
                    res = new Response(Response.StatusCode.FAIL);
                    res.data.put("messageDTO", errorMsg);
                }
            }
        }

        return res;
    }

    private Response processDelete(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                try{
                    petPlantAppService.deletePetPlant((PetPlantDTO) req.data.get("petPlantDTO"));
                    res = new Response(Response.StatusCode.SUCCESS);
                }catch(IllegalArgumentException e){
                    MessageDTO errorMsg = new MessageDTO(e.getMessage());
                    res = new Response(Response.StatusCode.FAIL);
                    res.data.put("messageDTO", errorMsg);
                }
            }
        }

        return res;
    }
}
