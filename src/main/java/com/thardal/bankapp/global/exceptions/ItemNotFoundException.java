package com.thardal.bankapp.global.exceptions;

import com.thardal.bankapp.customer.enums.CustomerErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(CustomerErrorMessages message) {
        super(message.toString());
    }
}
