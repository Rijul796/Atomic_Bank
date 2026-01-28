package com.example.atomicbank.service;

import com.example.atomicbank.model.Account;
import com.example.atomicbank.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankingService {

    @Autowired
    private AccountRepository accountRepository;

    // @Transactional is the "Atomic" magic.
    // It guarantees that everything inside this method happens ALL AT ONCE.
    // If the code crashes halfway (e.g., after deducting money but before adding it),
    // the database will ROLLBACK to the original state. No money is lost.
    @Transactional
    public void transferFunds(Long fromAccountId, Long toAccountId, BigDecimal amount) {

        // 1. Validate parameters
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be positive");
        }

        // 2. Fetch the accounts
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        // 3. Check for sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // 4. Perform the logic (Deduct from A, Add to B)
        // Note: BigDecimal is immutable, so we must re-assign the result.
        BigDecimal newSenderBalance = fromAccount.getBalance().subtract(amount);
        BigDecimal newReceiverBalance = toAccount.getBalance().add(amount);

        fromAccount.setBalance(newSenderBalance);
        toAccount.setBalance(newReceiverBalance);

        // 5. Save changes
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}