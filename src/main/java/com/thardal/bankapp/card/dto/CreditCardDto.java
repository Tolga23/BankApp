package com.thardal.bankapp.card.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreditCardDto {
    private Long id;
    private Long customerId;
    private Long cardNo;
    private Long cvvNo;
    private Date expiryDate;

    private BigDecimal totalLimit;

    private BigDecimal availableCardLimit;

    private BigDecimal currentDebt;

    private BigDecimal minimumPaymentAmount;

    private Date cutOffDate;

    private Date dueDate;
}
