package com.thardal.bankapp.card.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditCardSaveRequestDto {

    private BigDecimal earning;

    private String cutOffDay;

}
