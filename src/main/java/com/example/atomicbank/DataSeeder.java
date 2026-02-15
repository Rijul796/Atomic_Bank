package com.example.atomicbank; // Make sure this matches your project's base package!

import com.example.atomicbank.model.Account;
import com.example.atomicbank.model.User;
import com.example.atomicbank.repository.AccountRepository;
import com.example.atomicbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Only run this if the database is completely empty
        if (userRepository.count() == 0) {
            System.out.println("🌱 Cloud Database is empty! Seeding initial users...");

            // 1. Create Admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password123"));
            userRepository.save(admin);

            Account adminAccount = new Account();
            adminAccount.setUser(admin);
            adminAccount.setBalance(new BigDecimal("1000.00"));
            accountRepository.save(adminAccount);

            // 2. Create Alice
            User alice = new User();
            alice.setUsername("alice");
            alice.setPassword(passwordEncoder.encode("password123"));
            userRepository.save(alice);

            Account aliceAccount = new Account();
            aliceAccount.setUser(alice);
            aliceAccount.setBalance(new BigDecimal("500.00"));
            accountRepository.save(aliceAccount);

            // 3. Create Bob
            User bob = new User();
            bob.setUsername("bob");
            bob.setPassword(passwordEncoder.encode("password123"));
            userRepository.save(bob);

            Account bobAccount = new Account();
            bobAccount.setUser(bob);
            bobAccount.setBalance(new BigDecimal("500.00"));
            accountRepository.save(bobAccount);

            System.out.println("✅ Database successfully seeded with Admin, Alice, and Bob!");
        }
    }
}
