package com.thardal.bankapp.account.dao;

import com.thardal.bankapp.account.entity.AccountMoneyTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMoneyTransferDao extends JpaRepository<AccountMoneyTransfer,Long> {
}
