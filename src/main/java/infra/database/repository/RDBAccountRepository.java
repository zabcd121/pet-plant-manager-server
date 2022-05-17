package infra.database.repository;

import domain.model.Account;
import domain.repository.AccountRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RDBAccountRepository extends AbstractRepository<Account> implements AccountRepository {
    @Override
    public Account findByID(long id){
        executeFindOne("fff", ps -> {
            ps.setString(3, "33");
        });
        return null;
    }

    @Override
    public long save(Account account) {
        return 0;
    }

    @Override
    protected Account restoreObject(ResultSet rs) {
        return null;
    }

    @Override
    protected List<Account> restoreList(ResultSet rs) {
        return null;
    }

}
