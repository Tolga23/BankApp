package com.thardal.bankapp.account.entity;

import com.thardal.bankapp.account.enums.AccountCurrencyType;
import com.thardal.bankapp.account.enums.AccountType;
import com.thardal.bankapp.global.entity.BaseEntity;
import com.thardal.bankapp.global.enums.GlobalStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNT")
@Data
public class Account extends BaseEntity {

    @Id
    @SequenceGenerator(name = "Account", sequenceName = "ACCOUNT_ID_SEQ")
    @GeneratedValue(generator = "Account")
    private Long id;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "IBAN_NO", length = 30)
    private String ibanNo;

    @Column(name = "CURRENT_BALANCE", precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_TYPE", length = 30)
    private AccountCurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", length = 30)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_TYPE", length = 30)
    private GlobalStatusType statusType;

}
