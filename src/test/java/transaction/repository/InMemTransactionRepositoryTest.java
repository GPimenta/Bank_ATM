package transaction.repository;

import org.junit.jupiter.api.Test;
import transaction.model.Transaction;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemTransactionRepositoryTest {

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

    @Test
    void create() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void findByAllFromAccountId() {
    }

    @Test
    void findByAllToAccountId() {
    }

    @Test
    void findByAllFromAndToAccountId() {
    }

    @Test
    void getTransactionFromAndToAccountId() {
    }
}