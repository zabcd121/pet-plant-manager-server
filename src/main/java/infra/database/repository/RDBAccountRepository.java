package infra.database.repository;

import domain.model.Account;
import domain.repository.AccountRepository;
import dto.AccountDTO;
import dto.ModelMapper;
import infra.database.option.Option;
import infra.database.option.account.PKOption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RDBAccountRepository extends AbstractRepository<Account> implements AccountRepository {
    private final static String TABLE_NAME = "accounts";
    private final static String ACCOUNT_PK = "account_PK";
    private final static String ACCOUNT_ID = "account_ID";
    private final static String ACCOUNT_PW = "account_PW";
    private final static String TOKEN = "token";
    private final static String ADDRESS = "address";
    private final static String X = "x";
    private final static String Y = "y";

    private final static String[] INSERT_OR_UPDATE_COLUMN_NAMES = {
            ACCOUNT_ID, ACCOUNT_PW,
            TOKEN, ADDRESS, X, Y
    };

    @Override
    public Account findByID(long id){
        return executeFindOne( SQLMaker.makeSelectSql(TABLE_NAME, new PKOption(id)));
    }

    @Override
    public List<Account> findByOption(Option... options) {
        return executeFindList(SQLMaker.makeSelectSql(TABLE_NAME, options));
    }

    @Override
    public long save(Account account) {
        AccountDTO dto = ModelMapper.<Account, AccountDTO>modelToDTO(account, AccountDTO.class);
        
        if(dto.getPk()<=0){
            return add(dto);
        }else{
            update(dto);
            return dto.getPk();
        }
    }

    private long add(AccountDTO dto) {
        return executeInsert(
                SQLMaker.makeInsertSql(
                        TABLE_NAME, INSERT_OR_UPDATE_COLUMN_NAMES
                ),
                ps -> {
                    ps.setString(1, dto.getId());
                    ps.setString(2, dto.getPassword());
                    ps.setString(3, dto.getToken());
                    ps.setString(4, dto.getAddress());
                    ps.setDouble(5, dto.getX());
                    ps.setDouble(6, dto.getY());
                }
        );
    }

    private void update(AccountDTO dto) {
        executeUpdateOrDelete(
                SQLMaker.makeUpdateSql(
                        TABLE_NAME, ACCOUNT_PK, INSERT_OR_UPDATE_COLUMN_NAMES
                ),
                ps -> {
                    ps.setString(1, dto.getId());
                    ps.setString(2, dto.getPassword());
                    ps.setString(3, dto.getToken());
                    ps.setString(4, dto.getAddress());
                    ps.setDouble(5, dto.getX());
                    ps.setDouble(6, dto.getY());
                    ps.setLong(7, dto.getPk());
                }
        );
    }

    @Override
    public void remove(Account account) {
        AccountDTO dto = ModelMapper.<Account, AccountDTO>modelToDTO(account, AccountDTO.class);

        executeUpdateOrDelete(
                SQLMaker.makeDeleteSql(
                        TABLE_NAME, ACCOUNT_PK
                ),
                ps -> {
                    ps.setLong(1, dto.getPk());
                }
        );
    }

    @Override
    protected Account restoreObject(ResultSet rs) throws SQLException {
        Account acc = null;

        while(rs.next()){
            acc = Account.builder(
                    rs.getString(ACCOUNT_ID),
                    rs.getString(ACCOUNT_PW)
            ).pk(rs.getLong(ACCOUNT_PK))
                    .token(rs.getString(TOKEN))
                    .address(rs.getString(ADDRESS))
                    .x(rs.getDouble(X))
                    .y(rs.getDouble(Y))
                    .build();
        }


        return acc;
    }

    @Override
    protected List<Account> restoreList(ResultSet rs) throws SQLException {
        List<Account> list = new ArrayList<>();

        while(rs.next()){
            Account acc = Account.builder(
                    rs.getString(ACCOUNT_ID),
                    rs.getString(ACCOUNT_PW)
            ).pk(rs.getLong(ACCOUNT_PK))
                    .token(rs.getString(TOKEN))
                    .address(rs.getString(ADDRESS))
                    .x(rs.getDouble(X))
                    .y(rs.getDouble(Y))
                    .build();

            list.add(acc);
        }

        return list;
    }

}
