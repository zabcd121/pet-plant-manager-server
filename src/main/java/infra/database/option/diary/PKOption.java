package infra.database.option.diary;

import infra.database.option.Option;

public class PKOption implements Option {

    private String query="diary_PK=";

    public PKOption(long diaryPK) {
        this.query += ("'" + diaryPK + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }

}
