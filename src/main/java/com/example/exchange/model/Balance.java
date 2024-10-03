package com.example.exchange.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "balances")
@Getter
@Setter
@Builder
public class Balance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id", nullable = false)
    private Long balanceId;

    @Column(name = "balance")
    BigDecimal balance;

    @Column(name = "currency")
    Currency currency;

    @Column(name = "exchange_date")
    LocalDate exchangeDate;
}
