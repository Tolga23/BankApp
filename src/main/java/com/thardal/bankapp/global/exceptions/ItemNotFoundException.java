package com.thardal.bankapp.global.exceptions;

import com.thardal.bankapp.global.enums.BaseErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends BusinessException {
    public ItemNotFoundException(BaseErrorMessages message) {
        super(message);
    }
}
