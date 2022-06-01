package infra.database.repository;

import domain.model.Diary;
import domain.repository.DiaryRepository;
import dto.ModelMapper;
import dto.DiaryDTO;
import infra.database.option.Option;
import infra.database.option.post.PKOption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class RDBADiaryRepository extends AbstractRepository<Diary> implements DiaryRepository {

        private final static String TABLE_NAME = "posts";
        private final static String POST_PK = "post_PK";
        private final static String PET_PK = "pet_PK";
        private final static String TITLE = "title";
        private final static String CONTENT = "content";
        private final static String POSTED_DATE = "posted_date";
        private final static String PHOTO = "photo";

        private final static String[] INSERT_OR_UPDATE_COLUMN_NAMES = {
                TITLE, CONTENT, PHOTO
        };

        private long add(DiaryDTO dto) {
            return executeInsert(
                    SQLMaker.makeInsertSql(
                            TABLE_NAME, INSERT_OR_UPDATE_COLUMN_NAMES
                    ),
                    ps -> {
                        ps.setLong(1, dto.getPetPk());
                        ps.setString(2, dto.getTitle());
                        ps.setString(3, dto.getContent());
                        ps.setDate(4, Date.valueOf(dto.getPostedDate()));
                        ps.setBinaryStream(8, new ByteArrayInputStream(dto.getImgBytes()));
                    }
            );
        }

        private void update(DiaryDTO dto) {
            executeUpdateOrDelete(
                    SQLMaker.makeUpdateSql(
                            TABLE_NAME, POST_PK, INSERT_OR_UPDATE_COLUMN_NAMES
                    ),
                    ps -> {
                        ps.setLong(1, dto.getPetPk());
                        ps.setString(2, dto.getTitle());
                        ps.setString(3, dto.getContent());
                        ps.setDate(4, Date.valueOf(dto.getPostedDate()));
                        ps.setBinaryStream(8, new ByteArrayInputStream(dto.getImgBytes()));
                    }
            );
        }

        @Override
        public Diary findByID(long id) {
            return executeFindOne( SQLMaker.makeSelectSql(TABLE_NAME, new PKOption(id)));
        }

        @Override
        public List<Diary> findByOption(Option... options) {
            return executeFindList(SQLMaker.makeSelectSql(TABLE_NAME, options));
        }

        @Override
        public long save(Diary diary) {
            DiaryDTO dto = ModelMapper.<Diary, DiaryDTO>modelToDTO(diary, DiaryDTO.class);

            if(dto.getPk()<=0){
                return add(dto);
            }else{
                update(dto);
                return dto.getPk();
            }
        }

        @Override
        public void remove(Diary diary) {
            DiaryDTO dto = ModelMapper.<Diary, DiaryDTO>modelToDTO(diary, DiaryDTO.class);

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
        protected Diary restoreObject(ResultSet rs) throws SQLException {
            Diary diary = null;

            while(rs.next()){
                Blob blob = rs.getBlob(PHOTO);
                byte[] imgBytes = new byte[(int) blob.length()];
                try{
                    imgBytes = blob.getBinaryStream().readAllBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                diary = Diary.builder()
                        .pk(rs.getLong(POST_PK))
                        .petPk(rs.getLong(PET_PK))
                        .title(rs.getString(TITLE))
                        .content(rs.getString(CONTENT))
                        .postedDate(rs.getDate(POSTED_DATE).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .imgBytes(imgBytes)
                        .build();
            }


            return diary;
        }

        @Override
        protected List<Diary> restoreList(ResultSet rs) throws SQLException {
            List<Diary> list = new ArrayList<>();
            Blob blob = rs.getBlob(PHOTO);
            byte[] imgBytes = new byte[(int) blob.length()];
            try{
                imgBytes = blob.getBinaryStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(rs.next()){
                Diary diary = Diary.builder()
                        .pk(rs.getLong(POST_PK))
                        .petPk(rs.getLong(PET_PK))
                        .title(rs.getString(TITLE))
                        .content(rs.getString(CONTENT))
                        .postedDate(rs.getDate(POSTED_DATE).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .imgBytes(imgBytes)
                        .build();

                list.add(diary);
            }

            return list;
        }


}
