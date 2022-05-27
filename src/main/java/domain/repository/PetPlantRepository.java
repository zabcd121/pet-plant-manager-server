package domain.repository;

import domain.model.PetPlant;

import java.util.List;
import java.util.Set;

public interface PetPlantRepository {

    List<PetPlant> findAll();
    PetPlant findByPetId(long petId);
    Set<PetPlant> findByAccPk(long accPk);
    PetPlant findBy(String petName);
    void save(PetPlant petPlant);
    void insert(PetPlant pet);
    void remove(PetPlant pet);

}
