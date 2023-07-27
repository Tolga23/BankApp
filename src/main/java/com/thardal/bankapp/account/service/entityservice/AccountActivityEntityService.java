package com.thardal.bankapp.account.service.entityservice;

import com.thardal.bankapp.account.dao.AccountActivityDao;
import com.thardal.bankapp.account.entity.AccountActivity;
import com.thardal.bankapp.global.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountActivityEntityService extends BaseEntityService<AccountActivity, AccountActivityDao> {
    public AccountActivityEntityService(AccountActivityDao dao) {
        super(dao);
    }
}
