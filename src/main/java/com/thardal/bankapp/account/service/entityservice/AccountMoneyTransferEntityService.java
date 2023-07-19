package com.thardal.bankapp.account.service.entityservice;

import com.thardal.bankapp.account.dao.AccountMoneyTransferDao;
import com.thardal.bankapp.account.entity.AccountMoneyTransfer;
import com.thardal.bankapp.global.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class AccountMoneyTransferEntityService extends BaseEntityService<AccountMoneyTransfer, AccountMoneyTransferDao> {
    public AccountMoneyTransferEntityService(AccountMoneyTransferDao dao) {
        super(dao);
    }
}
