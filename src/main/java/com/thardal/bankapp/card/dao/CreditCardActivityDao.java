package com.thardal.bankapp.card.dao;

import com.thardal.bankapp.card.entity.CreditCardActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CreditCardActivityDao extends JpaRepository<CreditCardActivity,Long> {

    List<CreditCardActivity> findAllByCreditCardIdAndTransactionDateBetween(Long creditCardId, Date startDate, Date endDate);

}
