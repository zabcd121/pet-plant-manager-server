package infra.database.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryMaker {
    PreparedStatement make(Connection conn) throws SQLException;
}
