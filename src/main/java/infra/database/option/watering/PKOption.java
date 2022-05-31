package infra.database.option.watering;

import infra.database.option.Option;

public class PKOption implements Option {
    private String query="watering_PK=";

    public PKOption(long wateringPK) {
        this.query += ("'" + wateringPK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
