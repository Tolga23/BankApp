package com.thardal.bankapp.customer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSaveRequestDto {
    private String name;
    private String surname;
    private Long identityNo;
    private String password;
}
