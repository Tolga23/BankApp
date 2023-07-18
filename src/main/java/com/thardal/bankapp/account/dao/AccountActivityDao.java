package com.thardal.bankapp.account.dao;

import com.thardal.bankapp.account.entity.AccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountActivityDao extends JpaRepository<AccountActivity,Long> {
}
