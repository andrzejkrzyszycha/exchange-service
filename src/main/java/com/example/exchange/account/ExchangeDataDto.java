package com.example.exchange.account;

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
