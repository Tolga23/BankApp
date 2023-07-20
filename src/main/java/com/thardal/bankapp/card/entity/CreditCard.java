package com.thardal.bankapp.card.entity;

import com.thardal.bankapp.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CREDIT_CARD")
@Data
public class CreditCard extends BaseEntity {

    @Id
    @SequenceGenerator(name = "CreditCard", sequenceName = "CREDIT_CARD_ID_SEQ")
    @GeneratedValue(generator = "CreditCard")
    private Long id;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "CARD_NO", nullable = false)
    private Long cardNo;

    @Column(name = "CVV_NO", nullable = false)
    private Long cvvNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRY_DATE", nullable = false)
    private Date expiryDate;

    @Column(name = "TOTAL_LIMIT", precision = 19, scale = 2)
    private BigDecimal totalLimit;

    @Column(name = "AVAILABLE_CARD_LIMIT", precision = 19, scale = 2)
    private BigDecimal availableCardLimit;

    @Column(name = "CURRENT_DEBT", precision = 19, scale = 2)
    private BigDecimal currentDebt;

    @Column(name = "MINIMUM_PAYMENT_AMOUNT", precision = 19, scale = 2)
    private BigDecimal minimumPaymentAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "CUTOFF_DATE", nullable = false)
    private Date cutOffDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "DUE_DATE", nullable = false)
    private Date dueDate;

}
