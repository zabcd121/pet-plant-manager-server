package domain.repository;

import domain.model.Watering;
import infra.database.option.Option;

import java.util.List;

public interface WateringRepository {
    Watering findByID(long id);
    List<Watering> findByOption(Option... options);
    long save(Watering watering);
    void remove(Watering watering);
}
