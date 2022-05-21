package infra.database.option.account;

import infra.database.option.Option;

public class TokenOption implements Option {
    private String query="token=";

    public TokenOption(String token) {
        this.query+= ("'"+token+"'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
