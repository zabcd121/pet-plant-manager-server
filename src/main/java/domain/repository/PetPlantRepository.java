package domain.repository;

import domain.model.PetPlant;
import infra.database.option.Option;

import java.util.List;

public interface PetPlantRepository {
    PetPlant findByID(long id);
    List<PetPlant> findByOption(Option ...options);
    long save(PetPlant petPlant);
    void remove(PetPlant petPlant);
}
