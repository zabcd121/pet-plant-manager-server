package domain.repository;

import domain.model.Account;

public interface AccountRepository {
    Account findByID(long id);
    long save(Account account);
}
