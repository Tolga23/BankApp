package com.thardal.bankapp.global.enums;

public enum GlobalErrorMessages implements BaseErrorMessages {
    ITEM_NOT_FOUND("Item not found"),
    ITEM_ALREADY_EXISTS("Item already exists");

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
