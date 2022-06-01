package infra.database.option.watering;

import infra.database.option.Option;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MonthDateOption implements Option {
    private String query = "DATE_FORMAT(watering_day, '%Y-%m') = ";

    public MonthDateOption(LocalDate date) {
        this.query += ("'" + date.toString().substring(0, 7) + "'");
    }

    @Override
    public String getQuery() {
        return query;
    }
}
