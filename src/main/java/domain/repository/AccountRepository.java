package domain.repository;

import domain.model.Account;

import java.sql.SQLException;

public interface AccountRepository {
    Account findByID(long id) throws SQLException;
    long save(Account account);
}
