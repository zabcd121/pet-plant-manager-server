package domain.service;

import domain.model.Account;
import domain.repository.AccountRepository;
import infra.database.option.account.IDOption;
import infra.database.option.account.TokenOption;

import java.util.UUID;

public class AccountDomainService {
    private AccountRepository accRepo;

    public AccountDomainService(AccountRepository accRepo) {
        this.accRepo = accRepo;
    }

    public Account login(String token, String id, String pwd){
        Account acc = null;

        if(token!=null){
            try{
                return accRepo.findByOption(new TokenOption(token)).get(0);
            }catch(IndexOutOfBoundsException e){
                e.printStackTrace();
                return null;
            }
        }

        try{
            acc = accRepo.findByOption(new IDOption(id)).get(0);
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }

        if(acc!=null && acc.checkPassword(pwd)){
            return acc;
        }else{
            return null;
        }
    }

    public boolean signUp(String id, String pwd){
        if(accRepo.findByOption(new IDOption(id)).size()>0){
            return false;
        }

        String token = UUID.randomUUID().toString();

        Account newAcc = Account.builder(id, pwd).token(token).build();

        long accPk = accRepo.save(newAcc);

        if(accPk>0){
            return true;
        }else{
            return false;
        }
    }
}
