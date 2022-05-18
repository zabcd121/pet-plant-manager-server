package infra.database.option.account;

import infra.database.option.Option;

public class PKOption implements Option {
    private String query="account_pk=";

    public PKOption(long accountID) {
        this.query+= ("'"+accountID+"'");
    }

    @Override
    public String getQuery() {
        return null;
    }
}
