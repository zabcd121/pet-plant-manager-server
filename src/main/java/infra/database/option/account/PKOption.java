package infra.database.option.account;

import infra.database.option.Option;

public class PKOption implements Option {
    private String query="account_PK=";

    public PKOption(long accountPK) {
        this.query += ("'" + accountPK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
