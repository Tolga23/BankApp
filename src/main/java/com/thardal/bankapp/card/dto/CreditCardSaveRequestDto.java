package com.thardal.bankapp.card.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreditCardSaveRequestDto {

    @NotNull
    private Long customerId;

    private BigDecimal earning;

    private String cutOffDay;

}
