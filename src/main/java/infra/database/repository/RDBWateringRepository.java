package infra.database.repository;

import domain.model.Watering;
import domain.repository.WateringRepository;
import dto.ModelMapper;
import dto.WateringDTO;
import infra.database.option.Option;
import infra.database.option.watering.PKOption;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RDBWateringRepository extends AbstractRepository<Watering> implements WateringRepository {
    private static final String TABLE_NAME = "waterings";
    private static final String WATERING_PK = "watering_PK";
    private static final String PET_PLANT_PK = "petplant_PK";
    private static final String WATERING_DAY = "watering_day";
    private static final String USER_PK = "user_PK";

    private final static String[] INSERT_OR_UPDATE_COLUMN_NAMES = {
            PET_PLANT_PK, WATERING_DAY, USER_PK
    };

    @Override
    public Watering findByID(long id) {
        return executeFindOne(
                SQLMaker.makeSelectSql(TABLE_NAME, new PKOption(id))
        );
    }

    @Override
    public List<Watering> findByOption(Option... options) {
        return executeFindList(
                SQLMaker.makeSelectSql(TABLE_NAME, options)
        );
    }

    @Override
    public long save(Watering watering) {
        WateringDTO dto = ModelMapper.<Watering, WateringDTO>modelToDTO(watering, WateringDTO.class);

        if(dto.getWateringPK()<=0){
            return add(dto);
        }else{
            update(dto);
            return dto.getWateringPK();
        }
    }

    private long add(WateringDTO dto) {
        return executeInsert(
                SQLMaker.makeInsertSql(TABLE_NAME, INSERT_OR_UPDATE_COLUMN_NAMES),
                ps -> {
                    ps.setLong(1, dto.getPetPlantPK());
                    ps.setDate(2, Date.valueOf(dto.getWateringDay()));
                    ps.setLong(3, dto.getUserPK());
                }
        );
    }

    private void update(WateringDTO dto) {
        executeUpdateOrDelete(
                SQLMaker.makeUpdateSql(TABLE_NAME, WATERING_PK, INSERT_OR_UPDATE_COLUMN_NAMES),
                ps -> {
                    ps.setLong(1, dto.getPetPlantPK());
                    ps.setDate(2, Date.valueOf(dto.getWateringDay()));
                    ps.setLong(3, dto.getUserPK());
                    ps.setLong(4, dto.getWateringPK());
                }
        );
    }

    @Override
    public void remove(Watering watering) {
        WateringDTO dto = ModelMapper.<Watering, WateringDTO>modelToDTO(watering, WateringDTO.class);

        executeUpdateOrDelete(
                SQLMaker.makeDeleteSql(TABLE_NAME, WATERING_PK),
                ps -> {
                    ps.setLong(1, dto.getWateringPK());
                }
        );
    }

    @Override
    protected Watering restoreObject(ResultSet rs) throws SQLException {
        Watering res = null;

        while(rs.next()){
            res = Watering.builder()
                    .wateringPK(rs.getLong(WATERING_PK))
                    .wateringDay(rs.getDate(WATERING_DAY).toLocalDate())
                    .petPlantPK(rs.getLong(PET_PLANT_PK))
                    .userPK(rs.getLong(USER_PK))
                    .build();
        }

        return res;
    }

    @Override
    protected List<Watering> restoreList(ResultSet rs) throws SQLException {
        List<Watering> list = new ArrayList<>();

        while(rs.next()){
            list.add(
                Watering.builder()
                        .wateringPK(rs.getLong(WATERING_PK))
                        .wateringDay(rs.getDate(WATERING_DAY).toLocalDate())
                        .petPlantPK(rs.getLong(PET_PLANT_PK))
                        .userPK(rs.getLong(USER_PK))
                        .build()
            );
        }

        return list;
    }
}
