package com.example.exchange.service;

import com.example.exchange.model.Account;
import com.example.exchange.model.Balance;
import com.example.exchange.model.Currency;
import com.example.exchange.model.User;
import com.example.exchange.repository.AccountRepository;
import com.example.exchange.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/";

    @Transactional
    public Account createAccount(Long userId, BigDecimal initialBalancePln) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setUser(user);
        account.setBalances(List.of(Balance.builder()
                        .balance(initialBalancePln)
                        .currency(com.example.exchange.model.Currency.PLN)
                        .exchangeDate(LocalDate.now())
                .build()));
        return account;
    }

    @Transactional
    public Account exchange(Long accountId, Currency sourceCurrency, BigDecimal amount, Currency destinationCurrency) {
        Account account = getAccount(accountId);

        BigDecimal rate = getExchangeRate(destinationCurrency);
        BigDecimal destinationAmount = amount.divide(rate, 2, RoundingMode.HALF_UP);

        account.setBalances(List.of(Balance.builder()
                        .balance(amount)
                        .currency(sourceCurrency)
                        .exchangeDate(LocalDate.now()).build(),
                Balance.builder()
                        .balance(destinationAmount)
                        .exchangeDate(LocalDate.now())
                        .currency(destinationCurrency)
                        .build()));
        return account;
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

