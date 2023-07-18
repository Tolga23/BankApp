package com.thardal.bankapp.account.entity;

import com.thardal.bankapp.account.enums.AcccountMoneyTransferType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT_MONEY_TRANSFER")
@Data
public class AccountMoneyTransfer {

    @Id
    @SequenceGenerator(name = "AccountMoneyTransfer", sequenceName = "MONEY_TRANSFER_ID_SEQ")
    @GeneratedValue(generator = "AccountMoneyTransfer")
    private Long id;

    @Column(name = "ACCOUNT_ID_FROM")
    private Long accountIdFrom;
    @Column(name = "ACCOUNT_ID_TO")
    private Long accountIdTo;
    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "MONEY_TRANSFER_TYPE", length = 30)
    private AcccountMoneyTransferType acccountMoneyTransferType;
}
