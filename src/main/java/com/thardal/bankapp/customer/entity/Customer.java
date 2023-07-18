package com.thardal.bankapp.customer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CUSTOMER")
@Data
public class Customer {

    @Id
    @SequenceGenerator(name = "Customer", sequenceName = "CUSTOMER_ID_SEQ")
    @GeneratedValue(generator = "customer_sequence")
    private Long id;

    @Column(name = "NAME" , length = 100, nullable = false)
    private String name;

    @Column(name = "SURNAME", length = 100, nullable = false)
    private String surname;

    @Column(name = "IDENTITY_NO", nullable = false)
    private Long identityNo;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

}
