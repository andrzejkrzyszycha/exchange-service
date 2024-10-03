package com.example.exchange.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Table(name = "balance")
@Getter
@Setter
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id", nullable = false)
    private Long balanceId;

    @Column(name = "balance")
    BigDecimal balance;

    @Column(name = "currency")
    Currency currency;
}
