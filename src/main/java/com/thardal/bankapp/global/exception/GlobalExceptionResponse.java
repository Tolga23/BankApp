package com.thardal.bankapp.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GlobalExceptionResponse {
    private Date errorDate;
    private String message;
    private String detail;
}
