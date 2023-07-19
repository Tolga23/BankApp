package com.thardal.bankapp.account.service.entityservice;

import com.thardal.bankapp.account.dao.AccountDao;
import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.global.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class AccountEntityService extends BaseEntityService<Account, AccountDao> {

    public AccountEntityService(AccountDao dao) {
        super(dao);
    }

}
