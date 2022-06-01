package infra.database.repository;

import domain.model.Diary;
import domain.repository.DiaryRepository;
import dto.ModelMapper;
import dto.DiaryDTO;
import infra.database.option.Option;
import infra.database.option.diary.PKOption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class RDBDiaryRepository extends AbstractRepository<Diary> implements DiaryRepository {

        private final static String TABLE_NAME = "diaries";
        private final static String DIARY_PK = "diary_PK";
        private final static String PET_PK = "pet_plant_PK";
        private final static String USER_PK = "user_PK";
        private final static String TITLE = "title";
        private final static String CONTENT = "content";
        private final static String DATE = "date";
        private final static String DIARY_IMG = "diary_img";

        private final static String[] INSERT_OR_UPDATE_COLUMN_NAMES = {
                USER_PK, PET_PK, TITLE, CONTENT, DATE, DIARY_IMG
        };

        private long add(DiaryDTO dto) {
            return executeInsert(
                    SQLMaker.makeInsertSql(
                            TABLE_NAME, INSERT_OR_UPDATE_COLUMN_NAMES
                    ),
                    ps -> {
                        ps.setLong(1, dto.getUserPK());
                        ps.setLong(2, dto.getPetPlantPK());
                        ps.setString(3, dto.getTitle());
                        ps.setString(4, dto.getContent());
                        ps.setDate(5, Date.valueOf(dto.getDate()));
                        ps.setBinaryStream(6, new ByteArrayInputStream(dto.getDiaryImg()));
                    }
            );
        }

        private void update(DiaryDTO dto) {
            executeUpdateOrDelete(
                    SQLMaker.makeUpdateSql(
                            TABLE_NAME, DIARY_PK, INSERT_OR_UPDATE_COLUMN_NAMES
                    ),
                    ps -> {
                        ps.setLong(1, dto.getUserPK());
                        ps.setLong(2, dto.getPetPlantPK());
                        ps.setString(3, dto.getTitle());
                        ps.setString(4, dto.getContent());
                        ps.setDate(5, Date.valueOf(dto.getDate()));
                        ps.setBinaryStream(6, new ByteArrayInputStream(dto.getDiaryImg()));
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
                            TABLE_NAME, DIARY_PK
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
                Blob blob = rs.getBlob(DIARY_IMG);
                byte[] imgBytes = new byte[(int) blob.length()];
                try{
                    imgBytes = blob.getBinaryStream().readAllBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                diary = Diary.builder()
                        .pk(rs.getLong(DIARY_PK))
                        .userPK(rs.getLong(USER_PK))
                        .petPlantPK(rs.getLong(PET_PK))
                        .title(rs.getString(TITLE))
                        .content(rs.getString(CONTENT))
                        .date(rs.getDate(DATE).toLocalDate())
                        .diaryImg(imgBytes)
                        .build();
            }


            return diary;
        }

        @Override
        protected List<Diary> restoreList(ResultSet rs) throws SQLException {
            List<Diary> list = new ArrayList<>();
            Blob blob = rs.getBlob(DIARY_IMG);
            byte[] imgBytes = new byte[(int) blob.length()];
            try{
                imgBytes = blob.getBinaryStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(rs.next()){
                Diary diary = Diary.builder()
                        .pk(rs.getLong(DIARY_PK))
                        .userPK(rs.getLong(USER_PK))
                        .petPlantPK(rs.getLong(PET_PK))
                        .title(rs.getString(TITLE))
                        .content(rs.getString(CONTENT))
                        .date(rs.getDate(DATE).toLocalDate())
                        .diaryImg(imgBytes)
                        .build();

                list.add(diary);
            }

            return list;
        }


}
