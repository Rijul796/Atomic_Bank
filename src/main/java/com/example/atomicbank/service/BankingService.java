package com.example.atomicbank.service;

import com.example.atomicbank.model.Account;
import com.example.atomicbank.model.Transaction;
import com.example.atomicbank.repository.AccountRepository;
import com.example.atomicbank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankingService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void transferFunds(Long fromId, Long toId, BigDecimal amount) {
        // PROTECTION 1: No transferring 0 or negative numbers
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be positive.");
        }

        // PROTECTION 2: No transferring to yourself
        if (fromId.equals(toId)) {
            throw new RuntimeException("You cannot transfer money to yourself.");
        }

        Account fromAccount = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account toAccount = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        // PROTECTION 3: The "Anti-Overdraft" Check
        // If (Balance < Amount), stop immediately.
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds. You only have $" + fromAccount.getBalance());
        }

        // Execute Transfer
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        // Save History
        Transaction transaction = new Transaction(fromId, toId, amount);
        transactionRepository.save(transaction);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Transactional
    public void deposit(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Deposit amount must be positive.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // Log System Deposit (Source ID -1)
        Transaction transaction = new Transaction(-1L, accountId, amount);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findBySourceAccountIdOrTargetAccountIdOrderByTimestampDesc(accountId, accountId);
    }

    public BigDecimal getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }
}