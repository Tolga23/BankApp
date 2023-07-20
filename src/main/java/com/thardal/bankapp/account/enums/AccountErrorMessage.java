package com.thardal.bankapp.account.enums;

import com.thardal.bankapp.global.enums.BaseErrorMessages;

public enum AccountErrorMessage implements BaseErrorMessages {
    INSUFFICIENT_BALANCE("Insufficient balance!");

    private String message;
    AccountErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
