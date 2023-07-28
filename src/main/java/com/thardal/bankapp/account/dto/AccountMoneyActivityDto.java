package com.thardal.bankapp.account.dto;

import com.thardal.bankapp.account.enums.AccountActivityType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountMoneyActivityDto {
    private Long accountId;
    private BigDecimal amount;
    private AccountActivityType accountActivityType;

}
