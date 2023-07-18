package com.thardal.bankapp.account.service.entityservice;

import com.thardal.bankapp.account.dao.AccountDao;
import com.thardal.bankapp.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountEntityService {

    private final AccountDao accountDao;

    public List<Account> findAll() {
        return accountDao.findAll();
    }

    public Optional<Account> findById(Long id){
        return accountDao.findById(id);
    }

    public Account save(Account account){
        return accountDao.save(account);
    }

    public void delete(Account account){
        accountDao.delete(account);
    }

    public boolean existsById(Long id) {
        return accountDao.existsById(id);
    }
}
