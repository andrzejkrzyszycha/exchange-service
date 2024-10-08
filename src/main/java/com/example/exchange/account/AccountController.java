package com.example.exchange.account;

import com.example.exchange.currency.Currency;
import com.example.exchange.exchange.ExchangeDataDto;
import com.example.exchange.exchange.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final ExchangeService exchangeService;

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestParam Long userId,
                                                    @RequestParam BigDecimal initialBalance,
                                                    @RequestParam String currency) {
        Account account = accountService.createAccount(userId, initialBalance, currency);
        return new ResponseEntity<>(AccountMapper.toDto(account), HttpStatus.CREATED);
    }

    @PostMapping("/exchange")
    public ResponseEntity<ExchangeDataDto> exchange(@RequestParam Long accountId,
                                                    @RequestParam Currency destinationCurrency) {
        ExchangeDataDto exchangeData = exchangeService.exchange(accountId, destinationCurrency);
        return ResponseEntity.ok(exchangeData);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccount(accountId);
        return ResponseEntity.ok(AccountMapper.toDto(account));
    }

}
