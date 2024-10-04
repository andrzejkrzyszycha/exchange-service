package com.example.exchange.account;

import com.example.exchange.currency.CurrencyUtil;
import com.example.exchange.currency.Currency;
import com.example.exchange.user.User;
import com.example.exchange.user.UserRepository;
import jakarta.transaction.Transactional;
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
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";

    @Transactional
    public Account createAccount(Long userId, BigDecimal initialBalance, String initialCurrency) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Wrong value of initial balance");
        }
        Currency currency = CurrencyUtil.CURRENCY_MAP.get(initialCurrency); // This might be stored in database
        if (currency == null) {
            throw new RuntimeException("Wrong currency code");
        }
        Account account = new Account();
        account.setUser(user);
        account.setCurrency(currency);
        account.setAmount(initialBalance);
        return account;
    }

    public ExchangeDataDto exchange(Long accountId, Currency destinationCurrency) {
        Account account = getAccount(accountId);
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

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public BigDecimal getExchangeRate(Currency currency) {
        ResponseEntity<String> response = restTemplate.getForEntity(NBP_API_URL + currency + "/", String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getJSONArray("rates").getJSONObject(0).getBigDecimal("mid");
    }

}

