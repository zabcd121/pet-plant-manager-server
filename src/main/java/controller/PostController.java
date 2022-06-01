package controller;

import application.NoticeAppService;
import application.PostAppService;
import domain.repository.*;
import dto.*;
import infra.network.Request;
import infra.network.Response;

import java.util.List;

public class PostController {
    private final PostAppService postAppService;

    public PostController(PostRepository postRepo) {
        postAppService = new PostAppService(postRepo);
    }

    public Response handle(Request req) {
        String secondLevel = URLParser.parseURLByLevel(req.url, 2);

        switch(secondLevel){
            case "myposts":{
                return processPost(req);
            }
            case "petpkposts":{
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

    private Response processPost(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                String errorMsg = null;
                PostDTO postDTO = null;

                try{
                    postDTO = postAppService.createPost((PostDTO) req.data.get("postDTO"));
                }catch(IllegalArgumentException e){
                    errorMsg = e.getMessage();
                }finally {
                    if(postDTO==null){
                        res = new Response(Response.StatusCode.FAIL);
                        res.data.put("messageDTO", new MessageDTO(errorMsg));
                    }else{
                        res = new Response(Response.StatusCode.SUCCESS);
                    }

                }
                res.data.put("postDTO", postDTO);
            }

            case GET:{
                List<PostDTO> resData = null;

                resData = postAppService.retrieveAll(req.token);

                if(resData.size()==0){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                }

                res.data.put("postList", resData);
            }
        }
        return res;
    }

    private Response processEachPet(Request req) {
        Response res = null;

        switch (req.method){
            case GET:{
                List<PostDTO> resData = null;

                resData = postAppService.retrieve(((PostDTO)req.data.get("PostDTO")).getPetPk());

                if(resData.size()==0){
                    res = new Response(Response.StatusCode.FAIL);
                }else{
                    res = new Response(Response.StatusCode.SUCCESS);
                }

                res.data.put("postList", resData);
            }
        }
        return res;
    }


    private Response processDelete(Request req) {
        Response res = null;

        switch (req.method){
            case POST :{
                try{
                    postAppService.delete((PostDTO) req.data.get("postDTO"));
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
                PostDTO resData = null;
                MessageDTO errorMsg = null;

                try{
                    resData = postAppService.update((PostDTO) req.data.get("postDTO"));
                    res = new Response(Response.StatusCode.SUCCESS);
                    res.data.put("postDTO", resData);
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
