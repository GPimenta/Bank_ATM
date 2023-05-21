package accounts.repository;

import accounts.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemAccountRepositoryTest {

    IAccountRepository repository = new InMemAccountRepository();
    Account account1 = new Account.Builder()
            .withCustomerId(1)
            .withAccountNumber("12345")
            .withBalance(0D)
            .withPasswordAccount("1234")
            .withSecondaryOwnersId(List.of(2, 3))
            .build();

    Account account2 = new Account.Builder()
            .withCustomerId(2)
            .withAccountNumber("54321")
            .withBalance(0D)
            .withPasswordAccount("4321")
            .withSecondaryOwnersId(List.of(1, 3))
            .build();

    Account account3 = new Account.Builder()
            .withCustomerId(3)
            .withAccountNumber("51423")
            .withBalance(0D)
            .withPasswordAccount("4132")
            .withSecondaryOwnersId(List.of(1, 2))
            .build();

    @BeforeEach
    void setup() {
        repository.create(account1);
        repository.create(account2);
        repository.create(account3);
    }
    @AfterEach
    void end() {
        repository.getAll().clear();
    }
    @Test
    void create() {
        assertEquals(account1, repository.getAll().stream().filter(account -> account.equals(account1)).findFirst().get());
    }

    @Test
    void findAccountByAccountNumber() {
        assertEquals(account1, repository.findAccountByAccountNumber(account1.getAccountNumber()).get());
        assertEquals(account2, repository.findAccountByAccountNumber(account2.getAccountNumber()).get());
        assertEquals(account3, repository.findAccountByAccountNumber(account3.getAccountNumber()).get());
    }

    @Test
    void findByHolderCustomerId() {
        assertEquals(account1, repository.findByHolderCustomerId(account1.getCustomerId()).get());
        assertEquals(account2, repository.findByHolderCustomerId(account2.getCustomerId()).get());
        assertEquals(account3, repository.findByHolderCustomerId(account3.getCustomerId()).get());
    }

    @Test
    void findBySecondaryCustomerId() {
        assertTrue(repository.findBySecondaryCustomerId(2).contains(account1));
        assertTrue(repository.findBySecondaryCustomerId(2).contains(account3));
        assertFalse(repository.findBySecondaryCustomerId(2).contains(account2));
    }

    @Test
    void findByAllCustomerId() {
        assertTrue(repository.findByAllCustomerId(2).contains(account1));
        assertTrue(repository.findByAllCustomerId(2).contains(account2));
        assertTrue(repository.findByAllCustomerId(2).contains(account3));
    }
}