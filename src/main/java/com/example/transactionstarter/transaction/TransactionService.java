package com.example.transactionstarter.transaction;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
@Service

public class TransactionService {
    private final TransactionRepository transactionRepository;

public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
}
public Transaction createTransaction(Transaction transaction) {

    if (transaction.getTransactionId() == null ||
            transaction.getTransactionId().isBlank()) {
        throw new IllegalArgumentException("Transaction ID is required");
    }

    if (transaction.getCustomerId() == null ||
            transaction.getCustomerId().isBlank()) {
        throw new IllegalArgumentException("Customer ID is required");
    }

    if (transaction.getAmount() == null ||
            transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Amount must be greater than zero");
    }

    if (transaction.getCurrency() == null ||
            transaction.getCurrency().isBlank()) {
        throw new IllegalArgumentException("Currency is required");
    }

    if (transaction.getTransactionType() == null ||
            transaction.getTransactionType().isBlank()) {
        throw new IllegalArgumentException("Transaction type is required");
    }

    if (transaction.getTransactionStatus() == null ||
            transaction.getTransactionStatus().isBlank()) {
        throw new IllegalArgumentException("Transaction status is required");
    }

    if (transactionRepository.existsById(transaction.getTransactionId())) {
        throw new IllegalArgumentException("Transaction ID already exists");
    }

    return transactionRepository.save(transaction);
}

public Transaction getTransaction(String transactionId) {
    return transactionRepository.findById(transactionId)
            .orElseThrow(() -> new RuntimeException("Transaction not found"));
}

public Transaction updateTransactionStatus(String transactionId, String status) {

    Transaction transaction = getTransaction(transactionId);

    if (status == null || status.isBlank()) {
        throw new IllegalArgumentException("Transaction status is required");
    }

    String currentStatus = transaction.getTransactionStatus();

    if (currentStatus.equals("COMPLETED") ||
            currentStatus.equals("FAILED") ||
            currentStatus.equals("CANCELLED")) {
        throw new IllegalArgumentException(
                "Transaction status cannot be changed once it is " + currentStatus
        );
    }

    if (!status.equals("PENDING") &&
            !status.equals("COMPLETED") &&
            !status.equals("FAILED") &&
            !status.equals("CANCELLED")) {
        throw new IllegalArgumentException("Invalid transaction status");
    }

    transaction.setTransactionStatus(status);

    return transactionRepository.save(transaction);
}

public List<Transaction> getCustomerTransactions(String customerId) {
    return transactionRepository.findByCustomerId(customerId);
}
}
