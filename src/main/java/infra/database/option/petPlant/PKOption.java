package infra.database.option.petPlant;

import infra.database.option.Option;

public class PKOption implements Option {
    private String query="petplant_PK=";

    public PKOption(long petPlantPK) {
        this.query += ("'" + petPlantPK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
