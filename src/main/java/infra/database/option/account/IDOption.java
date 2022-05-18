package infra.database.option.account;

import infra.database.option.Option;

public class IDOption implements Option {
    private String query="account_ID=";

    public IDOption(String accountID) {
        this.query+= ("'"+accountID+"'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
