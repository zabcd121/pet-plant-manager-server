package application;

import domain.model.Notice;
import domain.model.PetPlant;
import domain.model.Post;
import domain.repository.PostRepository;
import domain.service.PetPlantManageSystem;
import dto.AccountDTO;
import dto.ModelMapper;
import dto.NoticeDTO;
import dto.PostDTO;
import infra.database.option.account.PKOption;
import infra.database.option.account.TokenOption;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostAppService {

    private PostRepository postRepo;

    public PostAppService(PostRepository postRepo){
        this.postRepo = postRepo;
    }

    public PostDTO createPost(PostDTO postDTO){
        Post post = Post.builder()
                            .petPk(postDTO.getPetPk())
                            .title(postDTO.getTitle())
                            .content(postDTO.getContent())
                            .postedTime(postDTO.getPostedTime())
                            .imgBytes(postDTO.getImgBytes())
                            .build();

        long postPK = postRepo.save(post);

        postDTO.setPk(postPK);

        if(postPK>0){
            return postDTO;
        }else{
            return null;
        }
    }

    public void delete(PostDTO postDTO){
        Post post = postRepo.findByID(postDTO.getPk());
        postRepo.remove(post);
    }

    public List<PostDTO> retrieveAll(String token){
        List<Post> posts = postRepo.findByOption(new TokenOption(token));
        List<PostDTO> postDTOList = new ArrayList<>();

        for(Post p : posts){
            postRepo.save(p);
            postDTOList.add(ModelMapper.modelToDTO(p, PostDTO.class));
        }

        return postDTOList;
    }

    public List<PostDTO> retrieve(long petPK){
        List<Post> posts = postRepo.findByOption(new infra.database.option.petPlant.PKOption(petPK));
        List<PostDTO> postDTOList = new ArrayList<>();

        for(Post p : posts){
            postRepo.save(p);
            postDTOList.add(ModelMapper.modelToDTO(p, PostDTO.class));
        }

        return postDTOList;
    }

    public PostDTO update(PostDTO postDTO){
        Post post = postRepo.findByID(postDTO.getPk());

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImgBytes(postDTO.getImgBytes());
        post.setPetPk(postDTO.getPetPk());
        post.setPostedTime(postDTO.getPostedTime());

        postRepo.save(post);

        return ModelMapper.modelToDTO(post, PostDTO.class);
    }

}
