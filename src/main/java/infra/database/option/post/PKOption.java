package infra.database.option.post;

import infra.database.option.Option;

public class PKOption implements Option {

    private String query="post_PK=";

    public PKOption(long postPK) {
        this.query += ("'" + postPK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }

}
