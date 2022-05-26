package domain.repository;

import domain.model.PetPlant;

import java.util.List;

public interface PetPlantRepository {

    List<PetPlant> findAll();
    PetPlant findBy(int id);
    PetPlant findBy(String petName);
    void save(PetPlant petPlant);
    void insert(PetPlant pet);
    void remove(PetPlant pet);

}
