package infra.database.option.plant;

import infra.database.option.Option;

public class PKOption implements Option {
    private String query="plant_PK=";

    public PKOption(long plantPK) {
        this.query += ("'" + plantPK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
