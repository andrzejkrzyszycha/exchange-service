package com.example.exchange.account;

public class AccountMapper {

    public static AccountDto toDto(Account account){
        return AccountDto.builder()
                .accountId(account.getAccountId())
                .user(account.getUser())
                .amount(account.getAmount())
                .currency(account.getCurrency())
                .build();
    }
}
