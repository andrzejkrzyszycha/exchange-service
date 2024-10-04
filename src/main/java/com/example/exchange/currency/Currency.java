package com.example.exchange.currency;

public enum Currency {
    PLN("985"),
    USD("840");

    public final String code;

    private Currency(String code) {
        this.code = code;
    }
}
