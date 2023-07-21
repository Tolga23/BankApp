package com.thardal.bankapp.account.dao;

import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {

    List<Account> findAllByStatusType(GlobalStatusType statusType);

}
