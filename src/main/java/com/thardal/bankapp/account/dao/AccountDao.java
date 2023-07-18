package com.thardal.bankapp.account.dao;

import com.thardal.bankapp.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<Account,Long> {
}
