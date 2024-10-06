package com.example.exchange.exchange;

import com.example.exchange.account.Account;
import com.example.exchange.account.AccountMapper;
import com.example.exchange.account.AccountService;
import com.example.exchange.currency.Currency;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ExchangeService {

    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";

    private final AccountService accountService;
    private final RestTemplate restTemplate;


    // I don't see reason to persist those data.
    // Data will be retried on the fly because the rate may be different each time we ask it
    public ExchangeDataDto exchange(Long accountId, Currency destinationCurrency) {
        Account account = accountService.getAccount(accountId);
        // Additional validation for existing of currency
        BigDecimal rate = getExchangeRate(destinationCurrency);
        BigDecimal destinationAmount = account.getAmount().divide(rate, 2, RoundingMode.HALF_UP);

        return ExchangeDataDto.builder()
                .accountDto(AccountMapper.toDto(account))
                .destinationCurrency(destinationCurrency)
                .destinationAmount(destinationAmount)
                .exchangeRate(rate)
                .exchangeDate(LocalDateTime.now())
                .build();
    }

    // Add resilience layer or cache for call to 3rd party system -
    // it depends on specification and needs to be clarified
    // fe. to avoid online calls there could be a microservice which takes all rates daily and then this process could be local
    private BigDecimal getExchangeRate(Currency currency) {
        ResponseEntity<String> response = restTemplate.getForEntity(NBP_API_URL + currency + "/", String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getJSONArray("rates").getJSONObject(0).getBigDecimal("mid");
    }

}
