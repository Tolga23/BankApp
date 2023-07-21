package com.thardal.bankapp.card.dto;

import com.thardal.bankapp.card.enums.CreditCardActivityType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreditCardActivityDto {
    private Long creditCardId;

    private BigDecimal amount;

    private Date transactionDate;

    private String description;

    private CreditCardActivityType activityType;

}
