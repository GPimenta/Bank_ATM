package accounts.service;

import accounts.exceptions.AccountConflictException;
import accounts.exceptions.AccountNotFoundException;
import accounts.model.Account;
import accounts.repository.IAccountRepository;
import accounts.repository.InMemAccountRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    IAccountRepository accountRepository = new InMemAccountRepository();

    IAccountService serviceAccount = new AccountService(accountRepository);

    @Test
    void createAccount() throws AccountConflictException, AccountNotFoundException {
        Account account = serviceAccount.createAccount(1);

        assertEquals(accountRepository.findByHolderCustomerId(1).get(),account);
    }

    @Test
    void deleteAccount() throws AccountConflictException, AccountNotFoundException {
        Account account1 = serviceAccount.createAccount(1);
        Account account2 = serviceAccount.createAccount(2);
        Account account3 = serviceAccount.createAccount(3);
        Account account4 = serviceAccount.createAccount(4);

        serviceAccount.deleteAccount(2);

        AccountNotFoundException thrown = assertThrows(AccountNotFoundException.class, () -> serviceAccount.findAccountByHolderCustomerId(2), "TESTING");

        assertEquals("Account not found with customer Id: 2", thrown.getMessage());

    }

    @Test
    void getAccount() throws AccountConflictException, AccountNotFoundException {
        Account account1 = serviceAccount.createAccount(1);
        Account account2 = serviceAccount.createAccount(2);
        Account account3 = serviceAccount.createAccount(3);
        Account account4 = serviceAccount.createAccount(4);

        Account getAccount = serviceAccount.getAccount(2);

        assertEquals(account2, getAccount);

    }

    @Test
    void findAccountByHolderCustomerId() throws AccountConflictException, AccountNotFoundException {
        Account account1 = serviceAccount.createAccount(1);
        Account account2 = serviceAccount.createAccount(2);
        Account account3 = serviceAccount.createAccount(3);
        Account account4 = serviceAccount.createAccount(4);

        Account getAccount = serviceAccount.findAccountByHolderCustomerId(2);

        assertEquals(account2, getAccount);

    }

    @Test
    void findAccountsBySecondaryCustomerId() throws AccountConflictException {
        Account account1 = serviceAccount.createAccount(1);
        Account account2 = serviceAccount.createAccount(2);
        Account account3 = serviceAccount.createAccount(3);
        Account account4 = serviceAccount.createAccount(4);

        List<Integer> secondaryOwnersId1 = List.of(1, 2, 3);
        List<Integer> secondaryOwnersId2 = List.of(4, 5, 6);

        account1.setSecondaryOwnersId(secondaryOwnersId1);
        account2.setSecondaryOwnersId(secondaryOwnersId2);


        assertEquals("[" + account1.toString() + "]", serviceAccount.findAccountsBySecondaryCustomerId(2).toString());

    }

    @Test
    void findAllAccountByCustomerId() throws AccountConflictException {
        Account account1 = serviceAccount.createAccount(1);
        Account account2 = serviceAccount.createAccount(2);
        Account account3 = serviceAccount.createAccount(3);
        Account account4 = serviceAccount.createAccount(4);

        List<Integer> secondaryOwnersId1 = List.of(2);
        List<Integer> secondaryOwnersId2 = List.of(4);
        List<Integer> secondaryOwnersId3 = List.of(1);
        List<Integer> secondaryOwnersId4 = List.of(3);

        account1.setSecondaryOwnersId(secondaryOwnersId1);
        account2.setSecondaryOwnersId(secondaryOwnersId2);
        account3.setSecondaryOwnersId(secondaryOwnersId3);
        account4.setSecondaryOwnersId(secondaryOwnersId4);



        assertEquals("[" + account3.toString() + ", " + account1.toString() + "]",
                serviceAccount.findAllAccountByCustomerId(1).toString());

    }

    @Test
    void depositAccount() {
    }

    @Test
    void withdrawAccount() {
    }

    @Test
    void transferMoney() {
    }

    @Test
    void addSecondaryOwner() {
    }

    @Test
    void deleteSecondaryOwner() {
    }
}