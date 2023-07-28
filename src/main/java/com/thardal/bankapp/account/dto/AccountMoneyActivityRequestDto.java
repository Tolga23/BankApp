package com.thardal.bankapp.account.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountMoneyActivityRequestDto {
    private Long accountId;
    private BigDecimal amount;
}
