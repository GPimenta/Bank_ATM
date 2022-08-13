package transaction.service;

import org.junit.jupiter.api.Test;
import transaction.model.Transaction;
import transaction.repository.ITransactionRepository;
import transaction.repository.InMemTransactionRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    ITransactionRepository transactionRepository = new InMemTransactionRepository();
    ITransactionService transactionService = new TransactionService(transactionRepository);

    @Test
    void createTransaction() {
        Transaction transaction = transactionService.createTransaction(1,2,3, LocalDateTime.)
    }

    @Test
    void deleteTransaction() {
    }

    @Test
    void getTransaction() {
    }

    @Test
    void findByAllTransactionsFromAccountId() {
    }

    @Test
    void findByAllTransactionsToAccountId() {
    }

    @Test
    void findByAllTransactionsFromAndToAccountId() {
    }

    @Test
    void getTransactionFromAndToAccountId() {
    }
}