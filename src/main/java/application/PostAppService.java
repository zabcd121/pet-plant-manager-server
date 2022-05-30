package application;

import domain.model.Post;
import domain.repository.PostRepository;
import dto.AccountDTO;
import dto.PostDTO;
import infra.database.option.account.PKOption;

import java.io.File;
import java.util.Date;
import java.util.List;

public class PostAppService {

    private PostRepository postRepo;

    public PostAppService(PostRepository postRepo){
        this.postRepo = postRepo;
    }

    public void createPost(PostDTO postDTO){
        Post post = Post.builder()
                            .pk(postDTO.getPk())
                            .petPk(postDTO.getPetPk())
                            .title(postDTO.getTitle())
                            .content(postDTO.getContent())
                            .postedTime(postDTO.getPostedTime())
                            .photo(postDTO.getPhoto())
                            .build();

        postRepo.save(post);
    }

    public void delete(PostDTO postDTO){
        Post post = postRepo.findByID(postDTO.getPk());
        postRepo.remove(post);
    }

    public List<Post> retrieveAll(AccountDTO accDTO){
        List<Post> posts = postRepo.findByOption(new PKOption(accDTO.getPk()));

        return posts;
    }

    public Post retrieve(long id){
        Post post = postRepo.findByID(id);

        return post;
    }

    public void update(PostDTO postDTO){
        Post post = postRepo.findByID(postDTO.getPk());

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setPhoto(postDTO.getPhoto());
        post.setPetPk(postDTO.getPetPk());
        post.setPostedTime(postDTO.getPostedTime());

        postRepo.save(post);
    }

}
