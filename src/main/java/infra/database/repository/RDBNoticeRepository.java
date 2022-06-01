package infra.database.repository;

import domain.model.Account;
import domain.model.Notice;
import domain.repository.NoticeRepository;
import dto.AccountDTO;
import dto.ModelMapper;
import dto.NoticeDTO;
import infra.database.option.Option;
import infra.database.option.account.PKOption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class RDBNoticeRepository extends AbstractRepository<Notice> implements NoticeRepository {

    private final static String TABLE_NAME = "notices";
    private final static String NOTICE_PK = "notice_PK";
    private final static String TARGET_ACC_ID = "target_acc_ID";
    private final static String TARGET_PET_ID = "target_pet_ID";
    private final static String CONTENT = "content";
    private final static String NOTICED_TIME = "noticed_time";

    private final static String[] INSERT_OR_UPDATE_COLUMN_NAMES = {
            TARGET_PET_ID, CONTENT, NOTICED_TIME
    };

    @Override
    public Notice findByID(long id){
        return executeFindOne( SQLMaker.makeSelectSql(TABLE_NAME, new PKOption(id)));
    }

    @Override
    public List<Notice> findByOption(Option... options) {
        return executeFindList(SQLMaker.makeSelectSql(TABLE_NAME, options));
    }

    @Override
    public long save(Notice notice) {
        NoticeDTO dto = ModelMapper.<Notice, NoticeDTO>modelToDTO(notice, NoticeDTO.class);

        if(dto.getPk()<=0){
            return add(dto);
        }else{
            update(dto);
            return dto.getPk();
        }
    }



    private long add(NoticeDTO dto) {
        return executeInsert(
                SQLMaker.makeInsertSql(
                        TABLE_NAME, INSERT_OR_UPDATE_COLUMN_NAMES
                ),
                ps -> {
                    ps.setLong(1, dto.getTargetAccId());
                    ps.setLong(2, dto.getTargetPetId());
                    ps.setString(3, dto.getContent());
                    ps.setDate(4, Date.valueOf(dto.getNoticedTime()));
                }
        );
    }

    private void update(NoticeDTO dto) {
        executeUpdateOrDelete(
                SQLMaker.makeUpdateSql(
                        TABLE_NAME, NOTICE_PK, INSERT_OR_UPDATE_COLUMN_NAMES
                ),
                ps -> {
                    ps.setLong(1, dto.getTargetAccId());
                    ps.setLong(2, dto.getTargetPetId());
                    ps.setString(3, dto.getContent());
                    ps.setDate(4, Date.valueOf(dto.getNoticedTime()));
                }
        );
    }

    @Override
    public void remove() {
        executeUpdateOrDelete(
                SQLMaker.makeDeleteAllSql(
                        TABLE_NAME
                ),
                ps -> {

                }
        );
    }

    @Override
    protected Notice restoreObject(ResultSet rs) throws SQLException {
        Notice notice = null;

        while(rs.next()){
            notice = Notice.builder()
                        .pk(rs.getLong(NOTICE_PK))
                        .targetAccId(rs.getLong(TARGET_ACC_ID))
                        .targetPetId(rs.getLong(TARGET_PET_ID))
                        .noticedTime(rs.getDate(NOTICED_TIME).toLocalDate())
                        .build();
        }


        return notice;
    }

    @Override
    protected List<Notice> restoreList(ResultSet rs) throws SQLException {
        List<Notice> list = new ArrayList<>();

        while(rs.next()){
            Notice notice = Notice.builder()
                    .pk(rs.getLong(NOTICE_PK))
                    .targetAccId(rs.getLong(TARGET_ACC_ID))
                    .targetPetId(rs.getLong(TARGET_PET_ID))
                    .noticedTime(rs.getDate(NOTICED_TIME).toLocalDate())
                    .build();

            list.add(notice);
        }

        return list;
    }
}
