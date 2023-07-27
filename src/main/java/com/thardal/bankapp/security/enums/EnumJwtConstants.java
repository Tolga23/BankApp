package com.thardal.bankapp.security.enums;

public enum EnumJwtConstants {
    BEARER("Bearer ")
    ;

    private String value;

    EnumJwtConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
