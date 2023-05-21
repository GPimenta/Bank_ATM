package accounts.service;

import accounts.exceptions.AccountConflictException;
import accounts.model.Account;
import accounts.repository.IAccountRepository;
import accounts.repository.InMemAccountRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AccountServiceMockitoTest {

    @Test
    void createAccount() throws AccountConflictException {
        final InMemAccountRepository repositoryMock = mock(InMemAccountRepository.class);


        Account account1 = new Account.Builder()
                .withId(1)
                .withCustomerId(1)
                .withAccountNumber("12345")
                .withBalance(0D)
                .withPasswordAccount("1234")
                .withSecondaryOwnersId(List.of(2, 3))
                .build();

        when(repositoryMock.create(account1)).thenReturn(Optional.of(account1));

        final Account accountTest = new AccountService(repositoryMock).createAccount(1);


        assert(account1 == accountTest);

    }


}
