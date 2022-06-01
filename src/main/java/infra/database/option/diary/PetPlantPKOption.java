package infra.database.option.diary;

import infra.database.option.Option;

public class PetPlantPKOption implements Option {
    private String query="pet_plant_PK=";

    public PetPlantPKOption(long petPlantPK) {
        this.query += ("'" + petPlantPK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}

