package com.example.atomicbank.controller;

import com.example.atomicbank.dto.TransferRequest;
import com.example.atomicbank.model.Transaction;
import com.example.atomicbank.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/banking")
@CrossOrigin(origins = "http://localhost:5173")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    // POST http://localhost:8080/api/banking/transfer
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        bankingService.transferFunds(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );
        return ResponseEntity.ok("Transfer Successful");
    }
    // POST http://localhost:8080/api/banking/deposit
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransferRequest request) {
        // We reuse TransferRequest but only use 'toAccountId' and 'amount'
        bankingService.deposit(request.getToAccountId(), request.getAmount());
        return ResponseEntity.ok("Deposit Successful");
    }
    @GetMapping("/{accountId}/transactions")
    public List<Transaction> getHistory(@PathVariable Long accountId) {
        return bankingService.getTransactionHistory(accountId);
    }

    @GetMapping("/{accountId}/balance")
    public BigDecimal getBalance(@PathVariable Long accountId) {
        return bankingService.getBalance(accountId);
    }
}
