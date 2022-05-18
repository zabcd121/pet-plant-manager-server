package application;

import controller.AccountController;
import domain.model.Account;
import domain.repository.AccountRepository;
import dto.AccountDTO;
import dto.MessageDTO;
import infra.database.option.account.IDOption;

public class AccountAppService {
    private AccountRepository accRepo;

    public AccountAppService(AccountRepository accRepo) {
        this.accRepo = accRepo;
    }

    public AccountDTO login(AccountDTO accDTO){
        AccountDTO res = null;
        Account acc = null;
        try{
            acc = accRepo.findByOption(new IDOption(accDTO.getId())).get(0);
            System.out.println("acc = " + acc);
        }catch(Exception e){ // TODO : id가 없을 때 예외
        }

        if(acc!=null && acc.checkPassword(accDTO.getPassword())){
            res = AccountDTO.builder().token("token").build();
            System.out.println("로그인 성공");
            //TDO : 로그인 성공 메시지
        }else{
            System.out.println("로그인 실패");
            //TODO : 로그인 실패 메시지
        }

        return res;
    }
}
