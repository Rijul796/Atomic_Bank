package com.example.atomicbank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users") // Renames the table in DB to 'users'
@Getter @Setter // Lombok generates Getters/Setters automatically
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    // A user can have multiple accounts (e.g., Savings, Checking)
    // "mappedBy" tells Hibernate that the 'user' field in the Account class owns the relationship
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;
}