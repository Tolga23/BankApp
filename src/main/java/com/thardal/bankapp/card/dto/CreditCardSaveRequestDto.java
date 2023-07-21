package com.thardal.bankapp.card.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditCardSaveRequestDto {

    @NotNull
    private Long customerId;

    private BigDecimal earning;

    private String cutOffDay;

}
