package com.thardal.bankapp.global.exceptions;

import com.thardal.bankapp.global.enums.BaseErrorMessages;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@RequiredArgsConstructor
@Data
public class BusinessException extends RuntimeException {

    private final BaseErrorMessages baseErrorMessages;

}
