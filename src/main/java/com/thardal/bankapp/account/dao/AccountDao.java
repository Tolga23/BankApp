package com.thardal.bankapp.account.dao;

import com.thardal.bankapp.account.entity.Account;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {

    List<Account> findAllByStatusType(GlobalStatusType statusType);

    Page<Account> findAllByStatusType(GlobalStatusType statusType, Pageable pageable);

}
