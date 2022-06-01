package application;

import controller.AccountController;
import domain.model.Account;
import domain.repository.AccountRepository;
import domain.service.AccountDomainService;
import dto.AccountDTO;
import dto.MessageDTO;
import dto.ModelMapper;
import infra.database.option.account.IDOption;

public class AccountAppService {
    private AccountRepository accRepo;
    private AccountDomainService accDomainService;

    public AccountAppService(AccountRepository accRepo) {
        this.accRepo = accRepo;
        accDomainService = new AccountDomainService(accRepo);
    }

    public AccountDTO login(AccountDTO accDTO){
        AccountDTO res = null;

        Account acc = accDomainService.login(
                accDTO.getToken(), accDTO.getId(), accDTO.getPassword()
        );

        if(acc!=null){
            res = ModelMapper.<Account, AccountDTO>modelToDTO(acc, AccountDTO.class);
        }

        return res;
    }

    public boolean signup(AccountDTO accDTO){
        return accDomainService.signUp(accDTO.getId(), accDTO.getPassword());
    }

}
