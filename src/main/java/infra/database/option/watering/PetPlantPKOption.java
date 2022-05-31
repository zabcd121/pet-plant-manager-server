package infra.database.option.watering;

import infra.database.option.Option;

public class PetPlantPKOption implements Option {
    private String query="petplant_PK=";

    public PetPlantPKOption(long petPlantPK) {
        this.query += ("'" + petPlantPK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
