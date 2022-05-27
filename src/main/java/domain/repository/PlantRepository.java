package domain.repository;

import domain.model.Plant;

public interface PlantRepository {

    Plant findByPk(long pk);
}
