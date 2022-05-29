package infra.database.option.petPlant;

import infra.database.option.Option;

public class NameOption implements Option {
    private String query="name=";

    public NameOption(String name) {
        this.query += ("'" + name + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
