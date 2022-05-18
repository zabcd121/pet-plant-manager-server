package infra.database.repository;

import domain.model.Account;
import domain.repository.AccountRepository;
import infra.database.option.Option;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DSAccountRepository implements AccountRepository {
    private Map<Long, Account> data;
    private Map<String, Account> option;

    public DSAccountRepository() {
        this.data = new HashMap<>();
        option = new HashMap<>();
        data.put(1L, Account.builder("aa", "aa").pk(1).build());
        option.put("aa", Account.builder("aa", "aa").pk(1).build());
    }

    @Override
    public Account findByID(long id){
        return data.get(id);
    }

    @Override
    public List<Account> findByOption(Option... options) {
        List<Account> list = new ArrayList<>();
        list.add(option.get("aa"));
        return list;
    }

    @Override
    public long save(Account account) {
        return 0;
    }

    @Override
    public void remove(Account account) {

    }
}
