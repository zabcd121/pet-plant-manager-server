package domain.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Member {
    protected long pk;
    protected String id;
    protected String name;
    private Set<PetPlant> myPets = new HashSet<>();

}
