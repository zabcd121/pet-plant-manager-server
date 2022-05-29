package domain.repository;

import domain.model.Plant;
import infra.database.option.Option;
import java.util.List;

public interface PlantRepository {
    Plant findByID(long id);
    List<Plant> findByOption(Option... options);
    long save(Plant plant);
    void remove(Plant plant);
}
