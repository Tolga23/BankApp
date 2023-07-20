package com.thardal.bankapp.account.dto;

import com.thardal.bankapp.account.enums.AcccountMoneyTransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MoneyTransferDto {
    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private String description;
    private Date transactionDate;
    private AcccountMoneyTransferType transferType;
}
