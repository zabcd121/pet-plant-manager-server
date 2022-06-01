package infra.database.option.notice;

import infra.database.option.Option;

public class UserPKOption implements Option {
    private String query="target_acc_ID=";

    public UserPKOption(long targetAccID) {
        this.query += ("'" + targetAccID + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}