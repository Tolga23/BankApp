package com.thardal.bankapp.global.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements BaseModel, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    @Embedded
    private BaseAdditionalFields additionalFields;


}
