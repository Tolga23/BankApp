package com.thardal.bankapp.security.dto;

import lombok.Data;

@Data
public class SecurityLoginRequestDto {
    private String identityNo;
    private String password;
}
