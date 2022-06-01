package controller;

import application.PetPlantAppService;
import domain.repository.AccountRepository;
import domain.repository.PetPlantRepository;
import domain.repository.WateringRepository;
import dto.MessageDTO;
import dto.PetPlantDTO;
import dto.WateringDTO;
import infra.network.Request;
import infra.network.Response;

import java.util.List;

public class PetPlantController {
    private final PetPlantAppService petPlantAppService;

    public PetPlantController(PetPlantRepository petPlantRepo, AccountRepository accRepo, WateringRepository wateringRepo) {
        petPlantAppService = new PetPlantAppService(petPlantRepo, accRepo, wateringRepo);
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
            case "watering":{
                return processWatering(req);
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
                    res = new Response(Response.StatusCode.SUCCESS);
                    res.data.put("petPlantDTO", petPlantDTO);
                }catch(IllegalArgumentException e){
                    errorMsg = e.getMessage();
                    res = new Response(Response.StatusCode.FAIL);
                    res.data.put("messageDTO", new MessageDTO(errorMsg));
                }
                break;
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
                break;
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

    private Response processWatering(Request req) {
        Response res = null;

        switch (req.method){
            case GET :{
                List<WateringDTO> data =
                        petPlantAppService.retrieveWateringHistory((PetPlantDTO) req.data.get("petPlantDTO"));

                if(data.size()==0){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                    res.data.put("wateringDTOList", data);
                }
                break;
            }
            case POST:{
                try{
                    WateringDTO data = petPlantAppService.createWatering(
                            (WateringDTO) req.data.get("wateringDTO")
                    );

                    res = new Response(Response.StatusCode.SUCCESS);
                    res.data.put("wateringDTO", data);
                }catch (IllegalArgumentException e){
                    res = new Response(Response.StatusCode.FAIL);
                    res.data.put("messageDTO", new MessageDTO(e.getMessage()));
                }
                break;
            }

        }

        return res;
    }

}
