package infra.database.option.petPlant;

import infra.database.option.Option;

public class UserPKOption implements Option {
    private String query="user_PK=";

    public UserPKOption(long userPK) {
        this.query += ("'" + userPK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
