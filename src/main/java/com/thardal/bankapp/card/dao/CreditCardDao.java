package com.thardal.bankapp.card.dao;

import com.thardal.bankapp.card.entity.CreditCard;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Long> {

    List<CreditCard> findAllByStatusType(GlobalStatusType globalStatusType);
}
