package domain.repository;

import domain.model.Account;

import java.util.List;

public interface AccountRepository {
    public Account findByID(long id);
    public long save(Account account);
}
