package com.thardal.bankapp.customer.enums;

import com.thardal.bankapp.global.enums.BaseErrorMessages;

public enum CustomerErrorMessages implements BaseErrorMessages {
    CUSTOMER_ERROR_MESSAGES("Customer not found!");

    String message;
    CustomerErrorMessages(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
