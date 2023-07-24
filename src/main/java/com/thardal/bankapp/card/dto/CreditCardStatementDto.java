package com.thardal.bankapp.card.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Data
public class CreditCardStatementDto {
    private final String customerName;
    private final String customerSurname;
    private final Long cardNo;
    private final Date expiryDate;
    private final BigDecimal currentDebt;
    private final BigDecimal minimumPaymentAmount;
    private final Date cutOffDate;
    private final Date dueDate;

    private List<CreditCardActivityDto> creditCardActivityDtoList;
}
