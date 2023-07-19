package com.thardal.bankapp.account.dto;

import com.thardal.bankapp.account.enums.AcccountMoneyTransferType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyTransferSaveRequestDto {
    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private String description;
    private AcccountMoneyTransferType transferType;
}
