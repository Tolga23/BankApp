package com.thardal.bankapp.global.enums;

public enum GlobalErrorMessages implements BaseErrorMessages {
    ITEM_NOT_FOUND("Item not found"),
    ITEM_ALREADY_EXISTS("Item already exists"),
    DATE_COULD_NOT_BE_CONVERTED("Date could not be converted"),
    VALUE_COULD_NOT_BE_NEGATIVE("Value could not be negative"),
    PARAMETER_CANNOT_BE_NULL("Parameter cannot be null");

    private String message;

    GlobalErrorMessages(String message) {
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
