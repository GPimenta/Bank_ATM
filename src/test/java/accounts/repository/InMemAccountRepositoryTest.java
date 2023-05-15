package accounts.repository;

import accounts.model.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemAccountRepositoryTest {

    IAccountRepository accountRepository = new InMemAccountRepository();
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
        accountRepository.create(account1);
        accountRepository.create(account2);
        accountRepository.create(account3);
    }
    @Test
    void create() {
        assertEquals(account1, accountRepository.getAll().stream().findFirst().get());
    }

    @Test
    void findAccountByAccountNumber() {
        assertEquals(account1, accountRepository.findAccountByAccountNumber(account1.getAccountNumber()).get());
        assertEquals(account2, accountRepository.findAccountByAccountNumber(account2.getAccountNumber()).get());
        assertEquals(account3, accountRepository.findAccountByAccountNumber(account3.getAccountNumber()).get());
    }

    @Test
    void findByHolderCustomerId() {
        assertEquals(account1, accountRepository.findByHolderCustomerId(account1.getCustomerId()).get());
        assertEquals(account2, accountRepository.findByHolderCustomerId(account2.getCustomerId()).get());
        assertEquals(account3, accountRepository.findByHolderCustomerId(account3.getCustomerId()).get());
    }

    @Test
    void findBySecondaryCustomerId() {
        assertTrue(accountRepository.findBySecondaryCustomerId(2).contains(account1));
        assertTrue(accountRepository.findBySecondaryCustomerId(2).contains(account3));
        assertFalse(accountRepository.findBySecondaryCustomerId(2).contains(account2));
    }

    @Test
    void findByAllCustomerId() {
        assertTrue(accountRepository.findByAllCustomerId(2).contains(account1));
        assertTrue(accountRepository.findByAllCustomerId(2).contains(account2));
        assertTrue(accountRepository.findByAllCustomerId(2).contains(account3));
    }
}