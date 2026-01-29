package com.example.atomicbank.config;

import com.example.atomicbank.model.Account;
import com.example.atomicbank.model.User;
import com.example.atomicbank.repository.AccountRepository;
import com.example.atomicbank.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, AccountRepository accountRepo) {
        return args -> {
            // Check and create users individually
            if (userRepo.findByUsername("admin").isEmpty()) {
                createUser(userRepo, accountRepo, "admin", new BigDecimal("1000.00"));
                System.out.println("✅ Created Admin User");
            }

            if (userRepo.findByUsername("alice").isEmpty()) {
                createUser(userRepo, accountRepo, "alice", new BigDecimal("500.00"));
                System.out.println("✅ Created Alice");
            }

            if (userRepo.findByUsername("bob").isEmpty()) {
                createUser(userRepo, accountRepo, "bob", new BigDecimal("300.00"));
                System.out.println("✅ Created Bob");
            }
        };
    }

    private void createUser(UserRepository userRepo, AccountRepository accountRepo, String username, BigDecimal balance) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("password123"); // Sets the correct password
        userRepo.save(user);

        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber("ACC-" + username.toUpperCase());
        account.setBalance(balance);
        accountRepo.save(account);
    }
}