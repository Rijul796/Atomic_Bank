package com.example.atomicbank.repository;

import com.example.atomicbank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // Custom query to find account by the string number (e.g., "ACC-123")
    Optional<Account> findByAccountNumber(String accountNumber);
}