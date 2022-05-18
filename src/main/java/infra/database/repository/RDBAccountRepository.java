package infra.database.repository;

import domain.model.Account;
import domain.repository.AccountRepository;
import dto.AccountDTO;
import dto.ModelMapper;
import infra.database.option.Option;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RDBAccountRepository extends AbstractRepository<Account> implements AccountRepository {
    @Override
    public Account findByID(long id){
        String query = "SELECT * FROM accounts WHERE id=?";

        return executeFindOne(conn -> {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            return ps;
        });
    }

    @Override
    public List<Account> findByOption(Option... options) {
        String and = " AND ";
        String where = " WHERE ";
        StringBuilder query = new StringBuilder(
                "SELECT * FROM accounts"
        );

        for(int i=0; i<options.length; i++){
            if(i==0){
                query.append(where);
            }

            query.append(options[i].getQuery());

            if(i!=options.length-1){
                query.append(and);
            }
        }


        return executeFindList(conn -> {
            PreparedStatement ps = conn.prepareStatement(query.toString());
            return ps;
        });
    }

    @Override
    public long save(Account account) {
        AccountDTO dto = ModelMapper.<Account, AccountDTO>modelToDTO(account, AccountDTO.class);
        
        if(dto.getPk()==-1){
            return add(dto);
        }else{
            update(dto);
            return dto.getPk();
        }
    }

    private long add(AccountDTO dto) {
        String query = "INSERT INTO accounts (account_ID, account_PW) VALUES(?, ?)";

        return executeInsert(conn -> {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, dto.getId());
            ps.setString(2, dto.getPassword());
            return ps;
        });
    }

    private void update(AccountDTO dto) {
        String query = "UPDATE accounts SET account_ID=?, account_PW=? WHERE account_PK=?";

        executeUpdateOrDelete(conn -> {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, dto.getId());
            ps.setString(2, dto.getPassword());
            ps.setLong(3, dto.getPk());
            return ps;
        });
    }

    @Override
    public void remove(Account account) {
        AccountDTO dto = ModelMapper.<Account, AccountDTO>modelToDTO(account, AccountDTO.class);
        String query = "DELETE FROM accounts WHERE account_PK=?";

        executeUpdateOrDelete(conn -> {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, dto.getPk());
            return ps;
        });
    }

    @Override
    protected Account restoreObject(ResultSet rs) throws SQLException {
        long pk = 0;
        String id = "";
        String password = "";

        while(rs.next()){
            pk = rs.getLong("account_PK");
            id = rs.getString("account_ID");
            password = rs.getString("account_PW");
        }


        return Account.builder(id, password).pk(pk).build();
    }

    @Override
    protected List<Account> restoreList(ResultSet rs) throws SQLException {
        List<Account> list = new ArrayList<>();

        while(rs.next()){
            Account acc = Account.builder(
                    rs.getString("account_ID"),
                    rs.getString("account_PW")
            ).pk(rs.getLong("account_PK")).build();

            list.add(acc);
        }

        return list;
    }

}
