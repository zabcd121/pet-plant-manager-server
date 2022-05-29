package controller;

import application.PetPlantAppService;
import domain.repository.PetPlantRepository;
import dto.MessageDTO;
import dto.PetPlantDTO;
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
            case "mypetplant":{
                return processPetplant(req);
            }
            case "update":{
            }
            case "delete":{
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

            }
        }

        return res;
    }
}
