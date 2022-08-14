package transaction.service;

import org.junit.jupiter.api.Test;
import transaction.exceptions.TransactonConflictException;
import transaction.model.Transaction;
import transaction.repository.ITransactionRepository;
import transaction.repository.InMemTransactionRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    ITransactionRepository transactionRepository = new InMemTransactionRepository();
    ITransactionService transactionService = new TransactionService(transactionRepository);

    @Test
    void createTransaction() throws TransactonConflictException {
        LocalDateTime localDateTime1 = LocalDateTime.of(2000, 9, 01, 12, 00);
        LocalDateTime localDateTime2 = LocalDateTime.of(2000, 10, 01, 12, 00);
        Transaction transaction = transactionService.createTransaction(1,2,3,
                localDateTime1,"5");

        System.out.println(transaction.getId());

        Transaction transaction1 = transactionRepository.getById(0).get();

        assertEquals(transaction, transaction1);
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