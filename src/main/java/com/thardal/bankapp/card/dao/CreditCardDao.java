package com.thardal.bankapp.card.dao;

import com.thardal.bankapp.card.dto.CreditCardStatementDto;
import com.thardal.bankapp.card.entity.CreditCard;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Long> {

    List<CreditCard> findAllByStatusType(GlobalStatusType globalStatusType);

    CreditCard findByCardNoAndCvvNoAndExpiryDateAndStatusType(Long cardNo, Long cvvNo, Date expiryDate, GlobalStatusType statusType);


    @Query(
            " select " +
                    " new com.thardal.bankapp.card.dto.CreditCardStatementDto( " +
                    " customer.name," +
                    " customer.surname," +
                    " creditCard.cardNo," +
                    " creditCard.expiryDate, " +
                    " creditCard.currentDebt," +
                    " creditCard.minimumPaymentAmount," +
                    " creditCard.cutOffDate," +
                    " creditCard.dueDate " +
                    ") " +
                    " from CreditCard creditCard " +
                    " left join Customer customer on creditCard.customerId = customer.id " +
                    " where creditCard.id = :creditCardId "
    )
    CreditCardStatementDto getCreditCardDetails(Long creditCardId);


}
