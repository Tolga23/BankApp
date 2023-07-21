package com.thardal.bankapp.card.enums;

import com.thardal.bankapp.global.enums.BaseErrorMessages;

public enum CreditCardErrorMessage implements BaseErrorMessages {
    CREDIT_CARD_NOT_FOUND("Credit card not found"),
    INSUFFICIENT_CARD_LIMIT("Insufficient card limit!"),
    CREDIT_CARD_EXPIRED("Credit card expired.")
    ;

    private String message;

    CreditCardErrorMessage(String message) {
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
