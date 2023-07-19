package com.thardal.bankapp.account.dto;

import com.thardal.bankapp.account.enums.AccountActivityType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountActivityDto {

    private Long accountId;
    private BigDecimal amount;
    private Date transactionDate;
    private BigDecimal currentBalance;
    private AccountActivityType accountActivityType;

}
