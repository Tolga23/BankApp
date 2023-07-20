package com.thardal.bankapp.account.entity;

import com.thardal.bankapp.account.enums.AccountActivityType;
import com.thardal.bankapp.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT_ACTIVITY")
@Data
public class AccountActivity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "AccountActivity", sequenceName = "ACCOUNT_ACTIVITY_ID_SEQ")
    @GeneratedValue(generator = "AccountActivity")
    private Long id;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

    @Column(name = "CURRENT_BALANCE", precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_ACTIVITY_TYPE", length = 30)
    private AccountActivityType accountActivityType;
}
