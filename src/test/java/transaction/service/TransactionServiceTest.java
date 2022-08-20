package transaction.service;

import org.junit.jupiter.api.Test;
import transaction.exceptions.TransactionNotFoundException;
import transaction.exceptions.TransactonConflictException;
import transaction.model.Transaction;
import transaction.repository.ITransactionRepository;
import transaction.repository.InMemTransactionRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

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

        Transaction transaction1 = transactionRepository.getById(1).get();

        assertEquals(transaction, transaction1);
    }

    @Test
    void deleteTransaction() throws TransactonConflictException, TransactionNotFoundException {

        LocalDateTime localDateTime1 = LocalDateTime.of(2000, 9, 01, 12, 00);
        LocalDateTime localDateTime2 = LocalDateTime.of(2000, 10, 01, 12, 00);
        LocalDateTime localDateTime3 = LocalDateTime.of(2001, 10, 01, 12, 00);
        Transaction transaction1 = transactionService.createTransaction(1,2,3,
                localDateTime1,"5");
        Transaction transaction2 = transactionService.createTransaction(2,1,4,
                localDateTime2,"6");
        Transaction transaction3 = transactionService.createTransaction(3,2,5,
                localDateTime3,"7");

        transactionService.deleteTransaction(2);

        Collection<Transaction> transactions = transactionRepository.getAll();

        Collection<Transaction> transactions1 = List.of(transaction1, transaction3);

        assertEquals(transactions1, transactions);



    }

    @Test
    void getTransaction() throws TransactonConflictException, TransactionNotFoundException {
        LocalDateTime localDateTime1 = LocalDateTime.of(2000, 9, 01, 12, 00);
        LocalDateTime localDateTime2 = LocalDateTime.of(2000, 10, 01, 12, 00);
        LocalDateTime localDateTime3 = LocalDateTime.of(2001, 10, 01, 12, 00);
        Transaction transaction1 = transactionService.createTransaction(1,2,3,
                localDateTime1,"5");
        Transaction transaction2 = transactionService.createTransaction(2,1,4,
                localDateTime2,"6");
        Transaction transaction3 = transactionService.createTransaction(3,2,5,
                localDateTime3,"7");



        Transaction transactionTest = transactionService.getTransaction(2);

        assertEquals(transaction2, transactionTest);

    }

    @Test
    void findByAllTransactionsFromAccountId() throws TransactonConflictException {

        LocalDateTime localDateTime1 = LocalDateTime.of(2000, 9, 01, 12, 00);
        LocalDateTime localDateTime2 = LocalDateTime.of(2000, 10, 01, 12, 00);
        LocalDateTime localDateTime3 = LocalDateTime.of(2001, 10, 01, 12, 00);
        Transaction transaction1 = transactionService.createTransaction(1,2,3,
                localDateTime1,"5");
        Transaction transaction2 = transactionService.createTransaction(2,1,4,
                localDateTime2,"6");
        Transaction transaction3 = transactionService.createTransaction(3,2,5,
                localDateTime3,"7");


        Collection<Transaction> transactions = transactionService.findByAllTransactionsFromAccountId(1);

        assertEquals(transaction1, transactions.stream().findAny().get());


    }

    @Test
    void findByAllTransactionsToAccountId() throws TransactonConflictException {
        LocalDateTime localDateTime1 = LocalDateTime.of(2000, 9, 01, 12, 00);
        LocalDateTime localDateTime2 = LocalDateTime.of(2000, 10, 01, 12, 00);
        LocalDateTime localDateTime3 = LocalDateTime.of(2001, 10, 01, 12, 00);
        Transaction transaction1 = transactionService.createTransaction(1,2,3,
                localDateTime1,"5");
        Transaction transaction2 = transactionService.createTransaction(2,1,4,
                localDateTime2,"6");
        Transaction transaction3 = transactionService.createTransaction(3,2,5,
                localDateTime3,"7");

        Collection<Transaction> transactions = transactionService.findByAllTransactionsToAccountId(2);

        assertEquals(transaction1, transactions.stream().findAny().get());
    }

    @Test
    void findByAllTransactionsFromAndToAccountId() throws TransactonConflictException {

        LocalDateTime localDateTime1 = LocalDateTime.of(2000, 9, 01, 12, 00);
        LocalDateTime localDateTime2 = LocalDateTime.of(2000, 10, 01, 12, 00);
        LocalDateTime localDateTime3 = LocalDateTime.of(2001, 10, 01, 12, 00);
        Transaction transaction1 = transactionService.createTransaction(1,2,3,
                localDateTime1,"5");
        Transaction transaction2 = transactionService.createTransaction(2,1,4,
                localDateTime2,"6");
        Transaction transaction3 = transactionService.createTransaction(3,2,5,
                localDateTime3,"7");


        Collection<Transaction> transactions = transactionService.findByAllTransactionsFromAndToAccountId(1, 2);

        assertEquals(transaction1, transactions.stream().findAny().get());


    }

    @Test
    void getTransactionFromAndToAccountId() throws TransactonConflictException, TransactionNotFoundException {

        LocalDateTime localDateTime1 = LocalDateTime.of(2000, 9, 01, 12, 00);
        LocalDateTime localDateTime2 = LocalDateTime.of(2000, 10, 01, 12, 00);
        LocalDateTime localDateTime3 = LocalDateTime.of(2001, 10, 01, 12, 00);
        Transaction transaction1 = transactionService.createTransaction(1,2,3,
                localDateTime1,"5");
        Transaction transaction2 = transactionService.createTransaction(2,1,4,
                localDateTime2,"6");
        Transaction transaction3 = transactionService.createTransaction(3,2,5,
                localDateTime3,"7");

        Transaction transaction = transactionService.getTransactionFromAndToAccountId(1, localDateTime1,
                2);

        assertEquals(transaction1, transaction);

    }
}