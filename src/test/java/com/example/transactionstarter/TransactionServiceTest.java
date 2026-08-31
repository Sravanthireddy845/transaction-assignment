package com.example.transactionstarter;

import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void shouldCreateTransactionSuccessfully() {

        Transaction transaction = new Transaction(
                "TEST001",
                "CUST001",
                new BigDecimal("1000.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        Transaction saved = transactionService.createTransaction(transaction);

        assertEquals("TEST001", saved.getTransactionId());
        assertEquals("CUST001", saved.getCustomerId());
        assertEquals(new BigDecimal("1000.00"), saved.getAmount());
    }

    @Test
    void shouldRejectInvalidTransaction() {

        Transaction transaction = new Transaction(
                "TEST002",
                "CUST002",
                BigDecimal.ZERO,
                "INR",
                "PAYMENT",
                "PENDING"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(transaction)
        );
    }

    @Test
    void shouldRejectDuplicateTransactionId() {

        Transaction firstTransaction = new Transaction(
                "TEST003",
                "CUST003",
                new BigDecimal("500.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        transactionService.createTransaction(firstTransaction);

        Transaction duplicateTransaction = new Transaction(
                "TEST003",
                "CUST004",
                new BigDecimal("700.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(duplicateTransaction)
        );
    }

    @Test
    void shouldThrowExceptionWhenTransactionDoesNotExist() {

        assertThrows(
                RuntimeException.class,
                () -> transactionService.getTransaction("DOES_NOT_EXIST")
        );
    }

    @Test
    void shouldUpdateTransactionStatusSuccessfully() {

        Transaction transaction = new Transaction(
                "TEST004",
                "CUST004",
                new BigDecimal("800.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        transactionService.createTransaction(transaction);

        Transaction updated =
                transactionService.updateTransactionStatus(
                        "TEST004",
                        "COMPLETED"
                );

        assertEquals("COMPLETED", updated.getTransactionStatus());
    }

    @Test
    void shouldRejectStatusChangeAfterFinalStatus() {

        Transaction transaction = new Transaction(
                "TEST005",
                "CUST005",
                new BigDecimal("900.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        transactionService.createTransaction(transaction);

        transactionService.updateTransactionStatus(
                "TEST005",
                "COMPLETED"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.updateTransactionStatus(
                        "TEST005",
                        "FAILED"
                )
        );
    }

    @Test
    void shouldGetCustomerTransactions() {

        Transaction firstTransaction = new Transaction(
                "TEST006",
                "CUST006",
                new BigDecimal("100.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        Transaction secondTransaction = new Transaction(
                "TEST007",
                "CUST006",
                new BigDecimal("200.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        transactionService.createTransaction(firstTransaction);
        transactionService.createTransaction(secondTransaction);

        List<Transaction> transactions =
                transactionService.getCustomerTransactions("CUST006");

        assertEquals(2, transactions.size());
        assertTrue(
        transactions.stream()
                .anyMatch(t -> t.getTransactionId().equals("TEST006"))
);

assertTrue(
        transactions.stream()
                .anyMatch(t -> t.getTransactionId().equals("TEST007"))
);
    }
}