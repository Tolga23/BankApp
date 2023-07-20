package com.thardal.bankapp.card.dao;

import com.thardal.bankapp.card.entity.CreditCardActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardActivityDao extends JpaRepository<CreditCardActivity,Long> {
}
