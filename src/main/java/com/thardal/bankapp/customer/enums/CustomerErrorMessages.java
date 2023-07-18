package com.thardal.bankapp.customer.enums;

public enum CustomerErrorMessages {
    CUSTOMER_ERROR_MESSAGES("Customer not found!");

    String message;
    CustomerErrorMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
