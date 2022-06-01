package infra.database.option.diary;

import infra.database.option.Option;

import java.time.LocalDate;

public class MonthDateOption implements Option {
    private String query = "DATE_FORMAT(date, '%Y-%m') = ";

    public MonthDateOption(LocalDate date) {
        this.query += ("'" + date.toString().substring(0, 7) + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
