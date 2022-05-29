package infra.database.repository;

import domain.model.PetPlant;
import domain.repository.PetPlantRepository;
import dto.ModelMapper;
import dto.PetPlantDTO;
import infra.database.option.Option;
import infra.database.option.petPlant.PKOption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RDBPetPlantRepository extends AbstractRepository<PetPlant> implements PetPlantRepository {
    private final static String TABLE_NAME = "petplants";
    private final static String PET_PLANT_PK = "petplant_PK";
    private final static String USER_PK = "user_PK";
    private final static String PLANT_PK = "plant_PK";
    private final static String NAME = "name";
    private final static String FIRST_MET_DAY = "first_met_day";
    private final static String IMAGE = "image";

    private final static String[] INSERT_OR_UPDATE_COLUMN_NAMES = {
            PLANT_PK, NAME, FIRST_MET_DAY, USER_PK, IMAGE
    };

    @Override
    public PetPlant findByID(long id) {
        return executeFindOne( SQLMaker.makeSelectSql(TABLE_NAME, new PKOption(id)));
    }

    @Override
    public List<PetPlant> findByOption(Option... options) {
        return executeFindList(SQLMaker.makeSelectSql(TABLE_NAME, options));
    }

    @Override
    public long save(PetPlant petPlant) {
        PetPlantDTO dto = ModelMapper.<PetPlant, PetPlantDTO>modelToDTO(petPlant, PetPlantDTO.class);

        if (dto.getPk() <= 0) {
            return add(dto);
        } else {
            update(dto);
            return dto.getPk();
        }
    }

    private long add(PetPlantDTO dto) {
        return executeInsert(
                SQLMaker.makeInsertSql(TABLE_NAME, INSERT_OR_UPDATE_COLUMN_NAMES),
                ps -> {
                    ps.setLong(1, dto.getPlantID());
                    ps.setString(2, dto.getPetName());
                    ps.setDate(3, Date.valueOf(dto.getFirstMetDay()));
                    ps.setLong(4, dto.getUserID());
                    ps.setBinaryStream(5, new ByteArrayInputStream(dto.getPetImg()));
                }
        );
    }

    private void update(PetPlantDTO dto) {
        executeUpdateOrDelete(
                SQLMaker.makeUpdateSql(TABLE_NAME, PET_PLANT_PK, INSERT_OR_UPDATE_COLUMN_NAMES),
                ps -> {
                    ps.setLong(1, dto.getPlantID());
                    ps.setString(2, dto.getPetName());
                    ps.setDate(3, Date.valueOf(dto.getFirstMetDay()));
                    ps.setLong(4, dto.getUserID());
                    ps.setBinaryStream(5, new ByteArrayInputStream(dto.getPetImg()));
                    ps.setLong(6, dto.getPk());
                }
        );
    }

    @Override
    public void remove(PetPlant petPlant) {
        PetPlantDTO dto = ModelMapper.<PetPlant, PetPlantDTO>modelToDTO(petPlant, PetPlantDTO.class);

        executeUpdateOrDelete(
                SQLMaker.makeDeleteSql(TABLE_NAME, PET_PLANT_PK),
                ps -> {
                    ps.setLong(1, dto.getPk());
                }
        );
    }

    @Override
    protected PetPlant restoreObject(ResultSet rs) throws SQLException {
        PetPlant petPlant = null;

        while(rs.next()){
            Blob blob = rs.getBlob(IMAGE);
            byte[] imgBytes = new byte[(int) blob.length()];
            try{
                imgBytes = blob.getBinaryStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            petPlant = PetPlant.builder(
                    rs.getLong(PLANT_PK),
                    rs.getLong(USER_PK),
                    rs.getString(NAME),
                    rs.getDate(FIRST_MET_DAY).toLocalDate()
            ).petImg(imgBytes)
            .build();
        }

        return petPlant;
    }

    @Override
    protected List<PetPlant> restoreList(ResultSet rs) throws SQLException {
        List<PetPlant> list = new ArrayList<>();

        while(rs.next()){
            Blob blob = rs.getBlob(IMAGE);
            byte[] imgBytes = new byte[(int) blob.length()];
            try{
                imgBytes = blob.getBinaryStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            PetPlant petPlant = PetPlant.builder(
                    rs.getLong(PLANT_PK),
                    rs.getLong(USER_PK),
                    rs.getString(NAME),
                    rs.getDate(FIRST_MET_DAY).toLocalDate()
            ).petImg(imgBytes)
            .build();

            list.add(petPlant);
        }

        return list;
    }
}
