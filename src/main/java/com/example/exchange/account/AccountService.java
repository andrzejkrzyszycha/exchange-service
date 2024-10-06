package com.example.exchange.account;

import com.example.exchange.currency.Currency;
import com.example.exchange.currency.CurrencyUtil;
import com.example.exchange.user.User;
import com.example.exchange.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    public Account createAccount(Long userId, BigDecimal initialBalance, String initialCurrency) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Wrong value of initial balance");
        }
        Currency currency = CurrencyUtil.CURRENCY_MAP.get(initialCurrency); // This should be stored in database
        if (currency == null) {
            throw new RuntimeException("Wrong currency code");
        }
        Account account = new Account();
        account.setUser(user);
        account.setCurrency(currency);
        account.setAmount(initialBalance);
        return accountRepository.save(account);
    }

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

}

