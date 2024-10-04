package com.example.exchange.account;

import com.example.exchange.currency.Currency;
import com.example.exchange.user.User;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class AccountDto {
    Long accountId;
    User user;
    Currency currency;
    BigDecimal amount;
}
