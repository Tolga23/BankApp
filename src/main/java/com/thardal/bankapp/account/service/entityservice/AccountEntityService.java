package com.thardal.bankapp.account.service.entityservice;

import com.thardal.bankapp.account.dao.AccountDao;
import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import com.thardal.bankapp.global.service.BaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountEntityService extends BaseEntityService<Account, AccountDao> {

    public AccountEntityService(AccountDao dao) {
        super(dao);
    }

    public List<Account> findAllActiveAccountList( ) {
        return getDao().findAllByStatusType(GlobalStatusType.ACTIVE);
    }

    public List<Account> findAllActiveAccountList(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);

        Page<Account> accountPage = getDao().findAllByStatusType(GlobalStatusType.ACTIVE, pageRequest);

        return accountPage.toList();
    }



    public List<Account> findAllByStatusType() {
        return getDao().findAllByStatusType(GlobalStatusType.ACTIVE);
    }



}
