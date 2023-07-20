package com.thardal.bankapp.card.dao;

import com.thardal.bankapp.card.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Long> {
}
