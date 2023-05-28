package accounts.service;

import accounts.exceptions.AccountConflictException;
import accounts.model.Account;
import accounts.repository.IAccountRepository;
import accounts.repository.InMemAccountRepository;
import org.junit.jupiter.api.Test;
import utils.INumbersGenerator;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AccountServiceMockitoTest {

    @Test
    void createAccount() throws AccountConflictException {
        final InMemAccountRepository repositoryMock = mock(InMemAccountRepository.class);
        final INumbersGenerator numbersGeneratorMock = mock(INumbersGenerator.class);
        int test = anyInt();
        System.out.println(test);


        Account account1 = new Account.Builder()
                .withCustomerId(1)
                .withAccountNumber(anyString())
                .withBalance(anyDouble())
                .withPasswordAccount(anyString())
                .withSecondaryOwnersId(List.of(2, 3))
                .build();

        when(repositoryMock.create(account1)).thenReturn(Optional.of(account1));

        final Account accountTest = new AccountService(repositoryMock).createAccount(1);


        assert(account1 == accountTest);

    }


}
