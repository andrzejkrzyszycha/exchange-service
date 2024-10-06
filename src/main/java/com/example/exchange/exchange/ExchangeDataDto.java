package com.example.exchange.exchange;

import com.example.exchange.account.AccountDto;
import com.example.exchange.currency.Currency;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class ExchangeDataDto {
    AccountDto accountDto;
    Currency destinationCurrency;
    BigDecimal exchangeRate;
    BigDecimal destinationAmount;
    LocalDateTime exchangeDate;
}
