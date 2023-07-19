package com.thardal.bankapp.account.dto;

import com.thardal.bankapp.account.enums.AccountCurrencyType;
import com.thardal.bankapp.account.enums.AccountType;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private Long customerId;
    private String ibanNo;
    private BigDecimal currentBalance;
    private AccountCurrencyType currencyType;
    private AccountType accountType;
    private GlobalStatusType statusType;
}
