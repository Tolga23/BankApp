package com.thardal.bankapp.card.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreditCardSpendDto {
    private Long cardNo;
    private Long cvvNo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    private BigDecimal amount;
    private String description;
}
