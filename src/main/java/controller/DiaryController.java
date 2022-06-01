package controller;

import application.DiaryAppService;
import domain.repository.*;
import dto.*;
import infra.network.Request;
import infra.network.Response;

import java.util.List;

public class DiaryController {
    private final DiaryAppService diaryAppService;

    public DiaryController(DiaryRepository diaryRepo) {
        diaryAppService = new DiaryAppService(diaryRepo);
    }

    public Response handle(Request req) {
        String secondLevel = URLParser.parseURLByLevel(req.url, 2);

        switch(secondLevel){
            case "mydiary":{
                return processDiary(req);
            }
            case "petpkdiary":{
                return processEachPet(req);
            }
            case "delete":{
                return processDelete(req);
            }
            case "update":{
                return processUpdate(req);
            }

        }

        return null;
    }

    private Response processDiary(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                String errorMsg = null;
                DiaryDTO diaryDTO = null;

                try{
                    diaryDTO = diaryAppService.createDiary((DiaryDTO) req.data.get("diaryDTO"));
                }catch(IllegalArgumentException e){
                    errorMsg = e.getMessage();
                }finally {
                    if(diaryDTO ==null){
                        res = new Response(Response.StatusCode.FAIL);
                        res.data.put("messageDTO", new MessageDTO(errorMsg));
                    }else{
                        res = new Response(Response.StatusCode.SUCCESS);
                    }

                }
                res.data.put("diaryDTO", diaryDTO);
            }

            case GET:{
                List<DiaryDTO> resData = null;

                resData = diaryAppService.retrieveAll((AccountDTO) req.data.get("accountDTO"));

                if(resData.size()==0){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                }

                res.data.put("diaryList", resData);
            }
        }
        return res;
    }

    private Response processEachPet(Request req) {
        Response res = null;

        switch (req.method){
            case GET:{
                List<DiaryDTO> resData = null;

                resData = diaryAppService.retrieve(((DiaryDTO)req.data.get("diaryDTO")).getPetPlantPK());

                if(resData.size()==0){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                }

                res.data.put("diaryList", resData);
            }
        }
        return res;
    }


    private Response processDelete(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                try{
                    diaryAppService.delete((DiaryDTO) req.data.get("diaryDTO"));
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

    private Response processUpdate(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                DiaryDTO resData = null;
                MessageDTO errorMsg = null;

                try{
                    resData = diaryAppService.update((DiaryDTO) req.data.get("diaryDTO"));
                    res = new Response(Response.StatusCode.SUCCESS);
                    res.data.put("diaryDTO", resData);
                }catch (IllegalArgumentException e){
                    errorMsg = new MessageDTO(e.getMessage());
                    res = new Response(Response.StatusCode.FAIL);
                    res.data.put("messageDTO", errorMsg);
                }
            }
        }

        return res;
    }
}
