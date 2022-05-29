package infra.database.repository;

import domain.model.Plant;
import domain.repository.PlantRepository;
import dto.ModelMapper;
import dto.PlantDTO;
import infra.database.option.Option;
import infra.database.option.plant.PKOption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RDBPlantRepository extends AbstractRepository<Plant> implements PlantRepository {
    private final static String TABLE_NAME = "plants";
    private final static String NAME = "name";
    private final static String PLANT_PK = "plant_PK";
    private final static String HUMIDITY = "humidity";
    private final static String GROWTH_TMP = "growth_tmp";
    private final static String GROWTH_SPEED = "growth_speed";
    private final static String MANAGE_LEVEL = "manage_level";
    private final static String LIGHT_DEMAND = "light_demand";
    private final static String CLASS_CODE = "class_code";
    private final static String IMAGE = "image";

    private final static String[] INSERT_OR_UPDATE_COLUMN_NAMES = {
            NAME, HUMIDITY, GROWTH_TMP, GROWTH_SPEED,
            MANAGE_LEVEL, LIGHT_DEMAND, CLASS_CODE, IMAGE
    };


    @Override
    public Plant findByID(long id) {
        return executeFindOne( SQLMaker.makeSelectSql( TABLE_NAME, new PKOption(id)));
    }

    @Override
    public List<Plant> findByOption(Option... options) {
        return executeFindList(SQLMaker.makeSelectSql(TABLE_NAME, options));
    }

    @Override
    public long save(Plant plant) {
        PlantDTO dto = ModelMapper.<Plant, PlantDTO>modelToDTO(plant, PlantDTO.class);

        if (dto.getPk() <= 0) {
            return add(dto);
        } else {
            update(dto);
            return dto.getPk();
        }
    }

    private long add(PlantDTO dto) {
        return executeInsert(
                SQLMaker.makeInsertSql(TABLE_NAME, INSERT_OR_UPDATE_COLUMN_NAMES),
                ps -> {
                    ps.setString(1, dto.getPltName());
                    ps.setInt(2, dto.getHumidity());
                    ps.setInt(3, dto.getGrowthTp());
                    ps.setInt(4, dto.getGrowthSpeed());
                    ps.setInt(5, dto.getMngLevel());
                    ps.setFloat(6, dto.getLightDemand());
                    ps.setFloat(7, dto.getClCode());
                    ps.setBinaryStream(8, new ByteArrayInputStream(dto.getImgBytes()));
                }
        );
    }

    private void update(PlantDTO dto) {
        executeUpdateOrDelete(
                SQLMaker.makeUpdateSql(TABLE_NAME, PLANT_PK, INSERT_OR_UPDATE_COLUMN_NAMES),
                ps -> {
                    ps.setString(1, dto.getPltName());
                    ps.setInt(2, dto.getHumidity());
                    ps.setInt(3, dto.getGrowthTp());
                    ps.setInt(4, dto.getGrowthSpeed());
                    ps.setInt(5, dto.getMngLevel());
                    ps.setFloat(6, dto.getLightDemand());
                    ps.setFloat(7, dto.getClCode());
                    ps.setBinaryStream(8, new ByteArrayInputStream(dto.getImgBytes()));
                    ps.setLong(9, dto.getPk());
                }
        );
    }

    @Override
    public void remove(Plant plant) {
        PlantDTO dto = ModelMapper.<Plant, PlantDTO>modelToDTO(plant, PlantDTO.class);

        executeUpdateOrDelete(
                SQLMaker.makeDeleteSql(TABLE_NAME, PLANT_PK),
                ps -> {
                    ps.setLong(1, dto.getPk());
                }
        );
    }

    @Override
    protected Plant restoreObject(ResultSet rs) throws SQLException {
        Plant plant = null;

        while(rs.next()){
            Blob blob = rs.getBlob(IMAGE);
            byte[] imgBytes = new byte[(int) blob.length()];
            try{
                imgBytes = blob.getBinaryStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            plant = Plant.builder()
                    .pk(rs.getLong(PLANT_PK))
                    .pltName(rs.getString(NAME))
                    .humidity(rs.getInt(HUMIDITY))
                    .growthTp(rs.getInt(GROWTH_TMP))
                    .growthSpeed(rs.getInt(GROWTH_SPEED))
                    .mngLevel(rs.getInt(MANAGE_LEVEL))
                    .lightDemand(rs.getFloat(LIGHT_DEMAND))
                    .clCode(rs.getFloat(CLASS_CODE))
                    .imgBytes(imgBytes)
                    .build();
        }

        return plant;
    }

    @Override
    protected List<Plant> restoreList(ResultSet rs) throws SQLException {
        List<Plant> list = new ArrayList<>();

        while(rs.next()){
            Blob blob = rs.getBlob(IMAGE);
            byte[] imgBytes = new byte[(int) blob.length()];
            try{
                imgBytes = blob.getBinaryStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Plant plant = Plant.builder()
                    .pk(rs.getLong(PLANT_PK))
                    .pltName(rs.getString(NAME))
                    .humidity(rs.getInt(HUMIDITY))
                    .growthTp(rs.getInt(GROWTH_TMP))
                    .growthSpeed(rs.getInt(GROWTH_SPEED))
                    .mngLevel(rs.getInt(MANAGE_LEVEL))
                    .lightDemand(rs.getFloat(LIGHT_DEMAND))
                    .clCode(rs.getFloat(CLASS_CODE))
                    .imgBytes(imgBytes)
                    .build();

            list.add(plant);
        }

        return list;
    }
}
