package com.thardal.bankapp.log.entity;

import com.thardal.bankapp.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "LOG_DETAILS")
@Getter
@Setter
public class LogDetails extends BaseEntity {

    @Id
    @GeneratedValue(generator = "LogDetails")
    @SequenceGenerator(name = "LogDetails", sequenceName = "LOG_DETAILS_ID_SEQ")
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "DATE_TIME")
    private Date dateTime;
}
