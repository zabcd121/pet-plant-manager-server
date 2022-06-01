package infra.database.option.notice;

import infra.database.option.Option;

public class PKOption implements Option {
    private String query="notice_PK=";

    public PKOption(long noticePK) {
        this.query += ("'" + noticePK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
