package com.example.exchange.currency;

import java.util.HashMap;
import java.util.Map;

public interface CurrencyUtil {

    Map<String, Currency> CURRENCY_MAP = new HashMap<>(){{
        put("PLN", Currency.PLN);
        put("USD", Currency.USD);
    }};
}
