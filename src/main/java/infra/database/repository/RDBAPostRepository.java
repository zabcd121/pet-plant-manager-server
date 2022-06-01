package infra.database.repository;

import domain.model.Notice;
import domain.model.Post;
import domain.repository.NoticeRepository;
import domain.repository.PostRepository;
import dto.ModelMapper;
import dto.NoticeDTO;
import dto.PostDTO;
import infra.database.option.Option;
import infra.database.option.post.PKOption;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RDBAPostRepository extends AbstractRepository<Post> implements PostRepository {

        private final static String TABLE_NAME = "posts";
        private final static String POST_PK = "post_PK";
        private final static String PET_PK = "pet_PK";
        private final static String TITLE = "title";
        private final static String CONTENT = "content";
        private final static String POSTED_TIME = "posted_time";
        private final static String PHOTO = "photo";

        private final static String[] INSERT_OR_UPDATE_COLUMN_NAMES = {
                TITLE, CONTENT, PHOTO
        };

        private long add(PostDTO dto) {
            return executeInsert(
                    SQLMaker.makeInsertSql(
                            TABLE_NAME, INSERT_OR_UPDATE_COLUMN_NAMES
                    ),
                    ps -> {
                        ps.setLong(1, dto.getPetPk());
                        ps.setString(2, dto.getTitle());
                        ps.setString(3, dto.getContent());
                        ps.setDate(4, Date.valueOf(dto.getPostedTime()));
                        ps.setBinaryStream(8, new ByteArrayInputStream(dto.getImgBytes()));
                    }
            );
        }

        private void update(PostDTO dto) {
            executeUpdateOrDelete(
                    SQLMaker.makeUpdateSql(
                            TABLE_NAME, POST_PK, INSERT_OR_UPDATE_COLUMN_NAMES
                    ),
                    ps -> {
                        ps.setLong(1, dto.getPetPk());
                        ps.setString(2, dto.getTitle());
                        ps.setString(3, dto.getContent());
                        ps.setDate(4, Date.valueOf(dto.getPostedTime()));
                        ps.setBinaryStream(8, new ByteArrayInputStream(dto.getImgBytes()));
                    }
            );
        }

        @Override
        public Post findByID(long id) {
            return executeFindOne( SQLMaker.makeSelectSql(TABLE_NAME, new PKOption(id)));
        }

        @Override
        public List<Post> findByOption(Option... options) {
            return executeFindList(SQLMaker.makeSelectSql(TABLE_NAME, options));
        }

        @Override
        public long save(Post post) {
            PostDTO dto = ModelMapper.<Post, PostDTO>modelToDTO(post, PostDTO.class);

            if(dto.getPk()<=0){
                return add(dto);
            }else{
                update(dto);
                return dto.getPk();
            }
        }

        @Override
        public void remove(Post post) {
            PostDTO dto = ModelMapper.<Post, PostDTO>modelToDTO(post, PostDTO.class);

            executeUpdateOrDelete(
                    SQLMaker.makeDeleteSql(
                            TABLE_NAME, POST_PK
                    ),
                    ps -> {
                        ps.setLong(1, dto.getPk());
                    }
            );
        }


        @Override
        protected Post restoreObject(ResultSet rs) throws SQLException {
            Post post = null;

            while(rs.next()){
                Blob blob = rs.getBlob(PHOTO);
                byte[] imgBytes = new byte[(int) blob.length()];
                try{
                    imgBytes = blob.getBinaryStream().readAllBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                post = Post.builder()
                        .pk(rs.getLong(POST_PK))
                        .petPk(rs.getLong(PET_PK))
                        .title(rs.getString(TITLE))
                        .content(rs.getString(CONTENT))
                        .postedTime(rs.getTimestamp(POSTED_TIME).toLocalDateTime())
                        .imgBytes(imgBytes)
                        .build();
            }


            return post;
        }

        @Override
        protected List<Post> restoreList(ResultSet rs) throws SQLException {
            List<Post> list = new ArrayList<>();
            Blob blob = rs.getBlob(PHOTO);
            byte[] imgBytes = new byte[(int) blob.length()];
            try{
                imgBytes = blob.getBinaryStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(rs.next()){
                Post post = Post.builder()
                        .pk(rs.getLong(POST_PK))
                        .petPk(rs.getLong(PET_PK))
                        .title(rs.getString(TITLE))
                        .content(rs.getString(CONTENT))
                        .postedTime(rs.getTimestamp(POSTED_TIME).toLocalDateTime())
                        .imgBytes(imgBytes)
                        .build();

                list.add(post);
            }

            return list;
        }


}
