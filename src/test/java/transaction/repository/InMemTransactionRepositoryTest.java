package transaction.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transaction.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemTransactionRepositoryTest {

    ITransactionRepository repository = new InMemTransactionRepository();

    Transaction transaction1 = new Transaction.Builder()
            .withFromAccountId(1)
            .withToAccountId(2)
            .withCardId(1)
            .withTimeStamp(LocalDateTime.now())
            .withAmount("100")
            .build();

    Transaction transaction2 = new Transaction.Builder()
            .withFromAccountId(2)
            .withToAccountId(1)
            .withCardId(2)
            .withTimeStamp(LocalDateTime.now())
            .withAmount("200")
            .build();

    Transaction transaction3 = new Transaction.Builder()
            .withFromAccountId(3)
            .withToAccountId(1)
            .withCardId(3)
            .withTimeStamp(LocalDateTime.now())
            .withAmount("0")
            .build();

    Transaction transaction4 = new Transaction.Builder()
            .withFromAccountId(1)
            .withToAccountId(2)
            .withCardId(4)
            .withTimeStamp(LocalDateTime.now())
            .withAmount("300")
            .build();

    @BeforeEach
    void setup() {
        repository.create(transaction1);
        repository.create(transaction2);
        repository.create(transaction3);
        repository.create(transaction4);
    }
    @AfterEach
    void end() {
        repository.getAll().clear();
    }
    @Test
    void create() {
        assertEquals(transaction1, repository.getAll().stream().filter(transaction -> transaction.equals(transaction1)).findFirst().get());
        assertEquals(transaction2, repository.getAll().stream().filter(transaction -> transaction.equals(transaction2)).findFirst().get());
        assertEquals(transaction3, repository.getAll().stream().filter(transaction -> transaction.equals(transaction3)).findFirst().get());
        assertEquals(transaction4, repository.getAll().stream().filter(transaction -> transaction.equals(transaction4)).findFirst().get());
    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(transaction2.getId()));
        assertEquals(3, repository.getAll().size());
        assertEquals(transaction1, repository.getAll().stream().filter(transaction -> transaction.equals(transaction1)).findFirst().get());
        assertEquals(transaction3, repository.getAll().stream().filter(transaction -> transaction.equals(transaction3)).findFirst().get());
        assertEquals(transaction4, repository.getAll().stream().filter(transaction -> transaction.equals(transaction4)).findFirst().get());
    }

    @Test
    void update() {
        final Transaction transactionTest = transaction1;
        transactionTest.setAmount("10000");
        repository.update(transactionTest);
        assertEquals("10000", repository.getById(transaction1.getId()).get().getAmount());
    }

    @Test
    void getById() {
        assertEquals(transaction1, repository.getById(transaction1.getId()).get());
    }

    @Test
    void getAll() {
        assertTrue(repository.getAll().containsAll(List.of(transaction1, transaction2, transaction3, transaction4)));
    }

    @Test
    void findByAllFromAccountId() {
        assertTrue(repository.findByAllFromAccountId(1).containsAll(List.of(transaction1, transaction4)));
    }

    @Test
    void findByAllToAccountId() {
        assertTrue(repository.findByAllToAccountId(2).containsAll(List.of(transaction1, transaction4)));

    }

    @Test
    void findByAllFromAndToAccountId() {
        assertTrue(repository.findByAllFromAndToAccountId(1, 2).containsAll(List.of(transaction1, transaction4)));
    }

    @Test
    void getTransactionFromAndToAccountId() {
        assertEquals(transaction4, repository.getTransactionFromAndToAccountId(1, transaction4.getTimestamp(),2).get());
    }
}