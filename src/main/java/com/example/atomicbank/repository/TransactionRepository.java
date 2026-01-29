package com.example.atomicbank.repository;

import com.example.atomicbank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Find all transactions where this account ID was either the sender OR the receiver
    List<Transaction> findBySourceAccountIdOrTargetAccountIdOrderByTimestampDesc(Long id1, Long id2);
}