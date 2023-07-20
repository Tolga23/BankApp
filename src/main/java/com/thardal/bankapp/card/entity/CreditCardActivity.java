package com.thardal.bankapp.card.entity;

import com.thardal.bankapp.card.enums.CreditCardActivityType;
import com.thardal.bankapp.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CREDIT_CARD_ACTIVITY")
@Data
public class CreditCardActivity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "CreditCardActivity", sequenceName = "CREDIT_CARD_ACTIVITY_ID_SEQ")
    @GeneratedValue(generator = "CreditCardActivity")
    private Long id;

    @Column(name = "CREDIT_CARD_ID", nullable = false)
    private Long creditCardId;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    @Column(name = "CARD_ACTIVITY_TYPE", length = 30)
    @Enumerated(EnumType.STRING)
    private CreditCardActivityType activityType;


}
