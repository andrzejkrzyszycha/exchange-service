package com.example.exchange.account;

import com.example.exchange.currency.Currency;
import com.example.exchange.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "user_id")
    private User user;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "amount")
    private BigDecimal amount;
}
