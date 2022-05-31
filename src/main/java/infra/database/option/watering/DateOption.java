package infra.database.option.watering;

import infra.database.option.Option;

import java.time.LocalDate;

public class DateOption implements Option {
    private String query="watering_day=";

    public DateOption(LocalDate date) {
        this.query += ("'" + date + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
