package com.thardal.bankapp.account.dto;

import com.thardal.bankapp.account.enums.AccountCurrencyType;
import com.thardal.bankapp.account.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountSaveRequestDto {

    private BigDecimal currentBalance;
    private AccountCurrencyType currencyType;
    private AccountType accountType;
}
