package com.example.exchange.controller;

import com.example.exchange.model.Account;
import com.example.exchange.model.Currency;
import com.example.exchange.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestParam Long userId, @RequestParam BigDecimal initialBalancePln) {
        Account account = accountService.createAccount(userId, initialBalancePln);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/exchange")
    public ResponseEntity<Account> exchange(@RequestParam Long accountId,
                                            @RequestParam Currency sourceCurrency,
                                            @RequestParam BigDecimal amount,
                                            @RequestParam Currency destinationCurrency) {
        Account account = accountService.exchange(accountId, sourceCurrency, amount, destinationCurrency);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccount(accountId);
        return ResponseEntity.ok(account);
    }


}
