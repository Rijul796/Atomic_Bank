package com.example.atomicbank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter @Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    // CRITICAL: Never use Double for money.
    // Double is imprecise (1.00 - 0.90 = 0.09999999).
    // BigDecimal is exact.
    @Column(nullable = false)
    private BigDecimal balance;

    // Connects this account to a specific User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}