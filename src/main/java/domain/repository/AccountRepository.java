package domain.repository;

import domain.model.Account;
import infra.database.option.Option;

import java.util.List;

public interface AccountRepository {
    Account findByID(long id);
    List<Account> findByOption(Option... options);
    long save(Account account);
    void remove(Account account);
}
