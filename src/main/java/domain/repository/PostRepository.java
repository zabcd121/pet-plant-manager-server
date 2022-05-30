package domain.repository;

import domain.model.Post;
import infra.database.option.Option;

import java.util.List;

public interface PostRepository {

    Post findByID(long id);
    List<Post> findByOption(Option... options);
    long save(Post post);
    void remove(Post post);
}
