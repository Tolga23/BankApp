package com.thardal.bankapp.account.service.entityservice;

import com.thardal.bankapp.account.dao.AccountDao;
import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import com.thardal.bankapp.global.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountEntityService extends BaseEntityService<Account, AccountDao> {

    public AccountEntityService(AccountDao dao) {
        super(dao);
    }

    public List<Account> findAllActiveAccountList(GlobalStatusType statusType){
        return getDao().findAllByStatusType(statusType);
    }

    public List<Account> findAllByStatusType(){
        return getDao().findAllByStatusType(GlobalStatusType.ACTIVE);
    }

}
