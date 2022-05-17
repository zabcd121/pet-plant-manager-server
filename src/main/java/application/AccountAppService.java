package application;

import domain.model.Account;
import domain.repository.AccountRepository;
import dto.AccountDTO;

public class AccountAppService {
    private AccountRepository accRepo;

    public AccountAppService(AccountRepository accRepo) {
        this.accRepo = accRepo;
    }

    public void login(AccountDTO accDTO){
        Account acc = null;
        try{
            acc = accRepo.findByID(accDTO.getPk());
        }catch(Exception e){ // TODO : id가 없을 때 예외
        }

        if(acc.checkPassword(accDTO.getPassword())){
            //TODO : 로그인 성공 메시지
        }else{
            //TODO : 로그인 실패 메시지
        }
    }
}
