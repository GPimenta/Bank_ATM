package customerswithaccount.service;

import accounts.exceptions.*;
import accounts.model.Account;
import accounts.repository.InMemAccountRepository;
import accounts.service.AccountService;
import accounts.service.IAccountService;
import cards.exceptions.CardConflictException;
import cards.model.CreditCard;
import cards.model.DebitCard;
import cards.repository.InMemCardRepositoryImpl;
import cards.service.CardService;
import cards.service.ICardService;
import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.model.Customer;
import costumers.repository.InMemCustomerRepositoryImpl;
import costumers.service.CustomerService;
import costumers.service.ICustomerService;
import customerswithaccount.exceptions.CustomerWithAccountNotFoundException;
import customerswithaccount.model.CustomerWithAccount;
import customerswithaccount.repository.CustomerWithAccountRepository;
import customerswithaccount.repository.ICustomerWithAccountRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import transaction.exceptions.TransactonConflictException;
import transaction.model.Transaction;
import transaction.repository.InMemTransactionRepository;
import transaction.service.ITransactionService;
import transaction.service.TransactionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CustomerWithAccountServiceTest {

    @Test
    void createCustomerWithAccount() throws CustomerConflictException, CustomerNotFoundException, AccountConflictException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        Customer customerMock = new Customer.Builder()
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withId(1)
                .withAccountNumber(accountNumber)
                .withCustomerId(1)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        when(customerServiceMock.createCustomer(name, taxId, email, birthday)).thenReturn(customerMock);
        when(accountServiceMock.createAccount(customerMock.getId())).thenReturn(accountMock);

        CustomerWithAccount customerWithAccount = new CustomerWithAccount.Builder()
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .build();

        CustomerWithAccount customerWithAccountWithId = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .build();

        when(repositoryMock.create(customerWithAccount)).thenReturn(Optional.of(customerWithAccountWithId));

        final CustomerWithAccount customerWithAccountMock = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock)
                .createCustomerWithAccount(name, taxId, email, birthday);

        assert(customerWithAccountWithId == customerWithAccountMock);
    }

    @Test
    void createCustomerWithAccountDebitCard() throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "12345";
        final String pinDebitCard = "1234";

        Customer customerMock = new Customer.Builder()
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        when(customerServiceMock.createCustomer(name, taxId, email, birthday)).thenReturn(customerMock);
        when(accountServiceMock.createAccount(customerMock.getId())).thenReturn(accountMock);
        when(cardServiceMock.createDebitCard(accountMock.getId(), customerMock.getId())).thenReturn(debitCardMock);

        CustomerWithAccount customerWithAccount = new CustomerWithAccount.Builder()
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        CustomerWithAccount customerWithAccountWithId = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        when(repositoryMock.create(customerWithAccount)).thenReturn(Optional.of(customerWithAccountWithId));

        final CustomerWithAccount customerWithAccountMock = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock)
                .createCustomerWithAccountDebitCard(name, taxId, email, birthday);

        assert(customerWithAccountWithId == customerWithAccountMock);
    }

    @Test
    void createCustomerWithAccountCreditCard() throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String creditCardNumber = "12345";
        final String pinCreditCard = "1234";

        Customer customerMock = new Customer.Builder()
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        CreditCard creditCardMock = new CreditCard.Builder()
                .withCustomerId(1)
                .withCardNumber(creditCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinCreditCard)
                .withCashAdvance(250)
                .isUsed(false)
                .build();

        when(customerServiceMock.createCustomer(name, taxId, email, birthday)).thenReturn(customerMock);
        when(accountServiceMock.createAccount(customerMock.getId())).thenReturn(accountMock);
        when(cardServiceMock.createCreditCard(accountMock.getId(), customerMock.getId())).thenReturn(creditCardMock);

        CustomerWithAccount customerWithAccount = new CustomerWithAccount.Builder()
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withCreditCard(creditCardMock)
                .buildCreditCard();

        CustomerWithAccount customerWithAccountWithId = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withCreditCard(creditCardMock)
                .buildCreditCard();

        when(repositoryMock.create(customerWithAccount)).thenReturn(Optional.of(customerWithAccountWithId));

        final CustomerWithAccount customerWithAccountMock = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock)
                .createCustomerWithAccountCreditCard(name, taxId, email, birthday);

        assert(customerWithAccountWithId == customerWithAccountMock);
    }

    @Test
    void createCustomerWithAccountCards() throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";
        final String creditCardNumber = "12345";
        final String pinCreditCard = "1234";

        Customer customerMock = new Customer.Builder()
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        CreditCard creditCardMock = new CreditCard.Builder()
                .withCustomerId(1)
                .withCardNumber(creditCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinCreditCard)
                .withCashAdvance(250)
                .isUsed(false)
                .build();

        when(customerServiceMock.createCustomer(name, taxId, email, birthday)).thenReturn(customerMock);
        when(accountServiceMock.createAccount(customerMock.getId())).thenReturn(accountMock);
        when(cardServiceMock.createDebitCard(accountMock.getId(), customerMock.getId())).thenReturn(debitCardMock);
        when(cardServiceMock.createCreditCard(accountMock.getId(), customerMock.getId())).thenReturn(creditCardMock);

        CustomerWithAccount customerWithAccount = new CustomerWithAccount.Builder()
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .withCreditCard(creditCardMock)
                .buildAllCard();

        CustomerWithAccount customerWithAccountWithId = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .withCreditCard(creditCardMock)
                .buildAllCard();

        when(repositoryMock.create(customerWithAccount)).thenReturn(Optional.of(customerWithAccountWithId));

        final CustomerWithAccount customerWithAccountMock = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock)
                .createCustomerWithAccountCards(name, taxId, email, birthday);

        assert(customerWithAccountWithId == customerWithAccountMock);
    }

    @Test @Disabled
    void deleteCustomerWithAccount() throws CustomerWithAccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        when(repositoryMock.deleteById(anyInt())).thenReturn(true);

        final boolean deleted = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).deleteCustomerWithAccount(anyInt());

        assert(deleted);

    }

    @Test
    void notDeleteCustomerWithAccount() throws CustomerWithAccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        when(repositoryMock.deleteById(anyInt())).thenReturn(false);

        final boolean deleted = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).deleteCustomerWithAccount(anyInt());

        assert(!deleted);
    }

    @Test
    void addDebitCardToCustomerWithAccount() throws CardConflictException, CustomerWithAccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";

        Customer customerMock = new Customer.Builder()
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        when(repositoryMock.findCustomerWithAccountThroughAccountNumber(accountNumber)).thenReturn(Optional.ofNullable(customerWithAccountMock));

        assert customerWithAccountMock != null;
        when(cardServiceMock.createDebitCard(customerWithAccountMock.getAccount().getId(), customerWithAccountMock.getCustomer().getId())).thenReturn(debitCardMock);

        CustomerWithAccount customerWithAccountWithDebitCardMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        when(repositoryMock.update(customerWithAccountWithDebitCardMock)).thenReturn(Optional.of(customerWithAccountWithDebitCardMock));

        final CustomerWithAccount customerWithAccount = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).addDebitCardToCustomerWithAccount(accountNumber);

        assert(customerWithAccount == customerWithAccountWithDebitCardMock);
    }

    @Test
    void addCreditCardToCustomerWithAccount() throws CardConflictException, CustomerWithAccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String creditCardNumber = "12345";
        final String pinCreditCard = "1234";

        Customer customerMock = new Customer.Builder()
                .withId(1)
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withId(1)
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        CreditCard creditCardMock = new CreditCard.Builder()
                .withCustomerId(1)
                .withCardNumber(creditCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinCreditCard)
                .withCashAdvance(250)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .build();

        when(repositoryMock.findCustomerWithAccountThroughAccountNumber(accountNumber)).thenReturn(Optional.ofNullable(customerWithAccountMock));

        assert customerWithAccountMock != null;

        when(cardServiceMock.createCreditCard(customerWithAccountMock.getAccount().getId(), customerWithAccountMock.getCustomer().getId())).thenReturn(creditCardMock);

        CustomerWithAccount customerWithAccountWithCreditCardMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withCreditCard(creditCardMock)
                .buildCreditCard();

        when(repositoryMock.update(customerWithAccountWithCreditCardMock)).thenReturn(Optional.of(customerWithAccountWithCreditCardMock));

        final CustomerWithAccount customerWithAccount = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).addCreditCardToCustomerWithAccount(accountNumber);

        assert(customerWithAccount == customerWithAccountWithCreditCardMock);
    }

    @Test
    void addCardsToCustomerWithAccount() throws CardConflictException, CustomerWithAccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";
        final String creditCardNumber = "12345";
        final String pinCreditCard = "1234";

        Customer customerMock = new Customer.Builder()
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        CreditCard creditCardMock = new CreditCard.Builder()
                .withCustomerId(1)
                .withCardNumber(creditCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinCreditCard)
                .withCashAdvance(250)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .build();

        when(repositoryMock.findCustomerWithAccountThroughAccountNumber(accountNumber)).thenReturn(Optional.ofNullable(customerWithAccountMock));

        assert customerWithAccountMock != null;
        when(cardServiceMock.createDebitCard(customerWithAccountMock.getAccount().getId(), customerWithAccountMock.getCustomer().getId())).thenReturn(debitCardMock);
        when(cardServiceMock.createCreditCard(customerWithAccountMock.getAccount().getId(), customerWithAccountMock.getCustomer().getId())).thenReturn(creditCardMock);

        CustomerWithAccount customerWithAccountWithCardsMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .withCreditCard(creditCardMock)
                .buildAllCard();

        when(repositoryMock.update(customerWithAccountWithCardsMock)).thenReturn(Optional.ofNullable(customerWithAccountWithCardsMock));

        final CustomerWithAccount customerWithAccount = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).addCardsToCustomerWithAccount(accountNumber);

        assert(customerWithAccount == customerWithAccountWithCardsMock);
    }

    @Test
    void withdrawMoneyWithCard() throws AccountNoFundsException, AccountVoidWithdrawException, AccountConflictException, AccountNotFoundException, CustomerWithAccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";

        final Double amount = 50D;

        Customer customerMock = new Customer.Builder()
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        when(cardServiceMock.checkCardNumberAndPassword(debitCardNumber, pinDebitCard)).thenReturn(true);

        final CustomerWithAccountService customerWithAccountServiceMock = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock);

        when(repositoryMock.findCustomerWithAccountThroughCreditCard(debitCardNumber)).thenReturn(Optional.ofNullable(customerWithAccountMock));
        when(accountServiceMock.withdrawAccount(customerWithAccountServiceMock.findCustomerWithAccountThroughCardNumber(debitCardNumber).getAccount().getId(), amount)).thenReturn(accountMock);

        final boolean withdrawnMoney = customerWithAccountServiceMock.withdrawMoneyWithCard(debitCardNumber, pinDebitCard, 50D);

        assert(withdrawnMoney);
    }

    @Test
    void depositMoneyWithCard() throws AccountNoFundsException, AccountVoidWithdrawException, AccountConflictException, AccountNotFoundException, CustomerWithAccountNotFoundException, AccountVoidDepositException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";

        final Double amount = 50D;

        Customer customerMock = new Customer.Builder()
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        when(cardServiceMock.checkCardNumberAndPassword(debitCardNumber, pinDebitCard)).thenReturn(true);

        final CustomerWithAccountService customerWithAccountServiceMock = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock);

        when(repositoryMock.findCustomerWithAccountThroughDebitCard(debitCardNumber)).thenReturn(Optional.ofNullable(customerWithAccountMock));
        when(accountServiceMock.depositAccount(customerWithAccountServiceMock.findCustomerWithAccountThroughCardNumber(debitCardNumber).getAccount().getId(), amount)).thenReturn(accountMock);

        final boolean depositMoney = customerWithAccountServiceMock.depositMoneyWithCard(debitCardNumber, pinDebitCard, 50D);

        assert(depositMoney);
    }

    @Test @Disabled
    void transferMoneyWithCard() throws AccountNoFundsException, AccountVoidWithdrawException, AccountVoidDepositException, AccountConflictException, AccountNotFoundException, TransactonConflictException, CustomerWithAccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";

        final Double amount = 50D;

        Customer customerMock = new Customer.Builder()
                .withId(1)
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withId(1)
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withId(1)
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        final LocalDate birthday2 = LocalDate.of(1990, 8, 10);
        final String name2 = "Cratos";
        final String taxId2 = "987654321";
        final String email2 = "cratos@email.com";

        final String password2 = "4321";
        final String accountNumber2 = "54321";
        final Double balance2 = 200D;
        final List<Integer> secondaryAccounts2 = List.of(1, 3);

        final String debitCardNumber2 = "12345";
        final String pinDebitCard2 = "1234";

        Customer customerMock2 = new Customer.Builder()
                .withId(2)
                .withName(name2)
                .withTaxId(taxId2)
                .withEmail(email2)
                .withBirthday(birthday2)
                .build();

        Account accountMock2 = new Account.Builder()
                .withId(2)
                .withCustomerId(2)
                .withAccountNumber(accountNumber2)
                .withPasswordAccount(password2)
                .withBalance(balance2)
                .withSecondaryOwnersId(secondaryAccounts2)
                .build();

        DebitCard debitCardMock2 = new DebitCard.Builder()
                .withId(2)
                .withCustomerId(2)
                .withCardNumber(debitCardNumber2)
                .withAccountId(2)
                .withCustomerId(2)
                .withPin(pinDebitCard2)
                .isUsed(false)
                .build();

        Transaction transactionMock = new Transaction.Builder()
                .withId(1)
                .withFromAccountId(accountMock.getId())
                .withToAccountId(accountMock2.getId())
                .withCardId(debitCardMock.getId())
                .withAmount(String.valueOf(amount))
                .withTimeStamp(LocalDateTime.now())
                .build();

        CustomerWithAccount customerWithAccountMock2 = new CustomerWithAccount.Builder()
                .withId(2)
                .withCostumer(customerMock2)
                .withAccount(accountMock2)
                .withDebidCard(debitCardMock2)
                .buildDebitCard();



        when(cardServiceMock.checkCardNumberAndPassword(debitCardNumber, pinDebitCard)).thenReturn(true);
        when(repositoryMock.findCustomerWithAccountThroughDebitCard(debitCardNumber)).thenReturn(Optional.of(customerWithAccountMock));
        when(repositoryMock.findCustomerWithAccountThroughAccountNumber(accountNumber2)).thenReturn(Optional.ofNullable(customerWithAccountMock2));
        when(cardServiceMock.getCardByCardNumber(debitCardNumber)).thenReturn(debitCardMock);
        when(accountServiceMock.transferMoney(accountMock.getId(), accountMock2.getId(), amount)).thenReturn(accountMock2);
        when(transactionServiceMock.createTransaction(accountMock.getId(), accountMock2.getId(), debitCardMock.getId(), LocalDateTime.now(), String.valueOf(amount))).thenReturn(transactionMock);

        final boolean transferred = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).transferMoneyWithCard(debitCardNumber, pinDebitCard, accountNumber2, amount);

        assert(transferred);
    }


//    @Test
//    void testing() throws CustomerConflictException, CardConflictException, CustomerNotFoundException, AccountConflictException, CustomerWithAccountNotFoundException {
//        ICustomerWithAccountRepository repository = new CustomerWithAccountRepository();
//        ICustomerService customerService = new CustomerService(new InMemCustomerRepositoryImpl());
//        IAccountService accountService = new AccountService(new InMemAccountRepository());
//        ICardService cardService = new CardService(new InMemCardRepositoryImpl());
//        ITransactionService transactionService = new TransactionService(new InMemTransactionRepository());
//        CustomerWithAccountService customerWithAccountService = new CustomerWithAccountService(repository, customerService, accountService, cardService, transactionService);
//
//        final LocalDate birthday = LocalDate.of(2000, 10, 2);
//        final String name = "Brutos";
//        final String taxId = "123456789";
//        final String email = "brutos@email.com";
//
//        final LocalDate birthday2 = LocalDate.of(1990, 8, 10);
//        final String name2 = "Cratos";
//        final String taxId2 = "987654321";
//        final String email2 = "cratos@email.com";
//
//
//        final CustomerWithAccount customerWithAccount1 = customerWithAccountService.createCustomerWithAccountDebitCard(name, taxId, email, birthday);
//        final CustomerWithAccount customerWithAccount2 = customerWithAccountService.createCustomerWithAccountDebitCard(name2, taxId2, email2, birthday2);
//
//        assert(customerWithAccount1 == customerWithAccountService.findCustomerWithAccountThroughCardNumber(customerWithAccount1.getDebitCard().getCardNumber()));
//    }



    @Test
    void addSecondaryOwners() throws CustomerWithAccountNotFoundException, CustomerNotFoundException, AccountConflictException, AccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";

        Customer customerMock = new Customer.Builder()
                .withId(1)
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withId(1)
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withId(1)
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        final LocalDate birthday2 = LocalDate.of(1990, 8, 10);
        final String name2 = "Cratos";
        final String taxId2 = "987654321";
        final String email2 = "cratos@email.com";

        Customer customerMock2 = new Customer.Builder()
                .withId(2)
                .withName(name2)
                .withTaxId(taxId2)
                .withEmail(email2)
                .withBirthday(birthday2)
                .build();

        when(repositoryMock.findCustomerWithAccountThroughAccountNumber(accountNumber)).thenReturn(Optional.ofNullable(customerWithAccountMock));
        when(customerServiceMock.findByTaxId(customerMock2.getTaxId())).thenReturn(customerMock);
        when(accountServiceMock.addSecondaryOwner(accountMock.getId(), customerMock.getId())).thenReturn(accountMock);
        final boolean added = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).addSecondaryOwners(accountNumber, customerMock2.getTaxId());

        assert(added);
    }

    @Test
    void deleteSecondaryOwners() throws CustomerNotFoundException, AccountConflictException, AccountNotFoundException, CustomerWithAccountNotFoundException {
            final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
            final ICustomerService customerServiceMock = mock(ICustomerService.class);
            final IAccountService accountServiceMock = mock(IAccountService.class);
            final ICardService cardServiceMock = mock(ICardService.class);
            final ITransactionService transactionServiceMock = mock(ITransactionService.class);

            final LocalDate birthday = LocalDate.of(2000, 10, 2);
            final String name = "Brutos";
            final String taxId = "123456789";
            final String email = "brutos@email.com";

            final String password = "1234";
            final String accountNumber = "12345";
            final Double balance = 100D;
            final List<Integer> secondaryAccounts= List.of(2, 3);

            final String debitCardNumber = "54321";
            final String pinDebitCard = "4321";

            Customer customerMock = new Customer.Builder()
                    .withId(1)
                    .withName(name)
                    .withTaxId(taxId)
                    .withEmail(email)
                    .withBirthday(birthday)
                    .build();

            Account accountMock = new Account.Builder()
                    .withId(1)
                    .withCustomerId(1)
                    .withAccountNumber(accountNumber)
                    .withPasswordAccount(password)
                    .withBalance(balance)
                    .withSecondaryOwnersId(secondaryAccounts)
                    .build();

            DebitCard debitCardMock = new DebitCard.Builder()
                    .withId(1)
                    .withCustomerId(1)
                    .withCardNumber(debitCardNumber)
                    .withAccountId(1)
                    .withCustomerId(1)
                    .withPin(pinDebitCard)
                    .isUsed(false)
                    .build();

            CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                    .withId(1)
                    .withCostumer(customerMock)
                    .withAccount(accountMock)
                    .withDebidCard(debitCardMock)
                    .buildDebitCard();

        final LocalDate birthday2 = LocalDate.of(1990, 8, 10);
        final String name2 = "Cratos";
        final String taxId2 = "987654321";
        final String email2 = "cratos@email.com";

        Customer customerMock2 = new Customer.Builder()
                .withId(2)
                .withName(name2)
                .withTaxId(taxId2)
                .withEmail(email2)
                .withBirthday(birthday2)
                .build();

        when(repositoryMock.findCustomerWithAccountThroughAccountNumber(accountNumber)).thenReturn(Optional.ofNullable(customerWithAccountMock));
        when(customerServiceMock.findByTaxId(customerMock2.getTaxId())).thenReturn(customerMock);
        when(accountServiceMock.deleteSecondaryOwner(accountMock.getId(), customerMock.getId())).thenReturn(accountMock);
        final boolean deleted = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).deleteSecondaryOwners(accountNumber, customerMock2.getTaxId());

        assert(deleted);
    }

    @Test
    void findCustomerWithAccountThroughAccountNumber() throws CustomerWithAccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";

        Customer customerMock = new Customer.Builder()
                .withId(1)
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withId(1)
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withId(1)
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        when(repositoryMock.findCustomerWithAccountThroughAccountNumber(accountNumber)).thenReturn(Optional.ofNullable(customerWithAccountMock));
        final CustomerWithAccount customerWithAccount = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).findCustomerWithAccountThroughAccountNumber(accountNumber);

        assert(customerWithAccount == customerWithAccountMock);
    }

    @Test
    void findAllCustomerWithAccountThroughTaxId() {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";

        Customer customerMock = new Customer.Builder()
                .withId(1)
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withId(1)
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withId(1)
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        final String password2 = "4321";
        final String accountNumber2 = "54321";
        final Double balance2 = 200D;
        final List<Integer> secondaryAccounts2 = List.of(1, 3);

        final String debitCardNumber2 = "12345";
        final String pinDebitCard2 = "1234";

        Account accountMock2 = new Account.Builder()
                .withId(2)
                .withCustomerId(1)
                .withAccountNumber(accountNumber2)
                .withPasswordAccount(password2)
                .withBalance(balance2)
                .withSecondaryOwnersId(secondaryAccounts2)
                .build();

        DebitCard debitCardMock2 = new DebitCard.Builder()
                .withId(2)
                .withCustomerId(1)
                .withCardNumber(debitCardNumber2)
                .withAccountId(2)
                .withPin(pinDebitCard2)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock2 = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock2)
                .withDebidCard(debitCardMock2)
                .buildDebitCard();

        final List<CustomerWithAccount> customerWithAccountMockList = List.of(customerWithAccountMock, customerWithAccountMock2);
        when(repositoryMock.findAllCustomerWithAccountThroughTaxId(customerMock.getTaxId())).thenReturn(customerWithAccountMockList);
        final List<CustomerWithAccount> allCustomerWithAccountThroughTaxId = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).findAllCustomerWithAccountThroughTaxId(customerMock.getTaxId());

        assert(allCustomerWithAccountThroughTaxId == customerWithAccountMockList);
    }

    @Test
    void findCustomerWithAccountThroughCardNumber() throws CustomerWithAccountNotFoundException {
        final ICustomerWithAccountRepository repositoryMock = mock(ICustomerWithAccountRepository.class);
        final ICustomerService customerServiceMock = mock(ICustomerService.class);
        final IAccountService accountServiceMock = mock(IAccountService.class);
        final ICardService cardServiceMock = mock(ICardService.class);
        final ITransactionService transactionServiceMock = mock(ITransactionService.class);

        final LocalDate birthday = LocalDate.of(2000, 10, 2);
        final String name = "Brutos";
        final String taxId = "123456789";
        final String email = "brutos@email.com";

        final String password = "1234";
        final String accountNumber = "12345";
        final Double balance = 100D;
        final List<Integer> secondaryAccounts= List.of(2, 3);

        final String debitCardNumber = "54321";
        final String pinDebitCard = "4321";

        Customer customerMock = new Customer.Builder()
                .withId(1)
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();

        Account accountMock = new Account.Builder()
                .withId(1)
                .withCustomerId(1)
                .withAccountNumber(accountNumber)
                .withPasswordAccount(password)
                .withBalance(balance)
                .withSecondaryOwnersId(secondaryAccounts)
                .build();

        DebitCard debitCardMock = new DebitCard.Builder()
                .withId(1)
                .withCustomerId(1)
                .withCardNumber(debitCardNumber)
                .withAccountId(1)
                .withCustomerId(1)
                .withPin(pinDebitCard)
                .isUsed(false)
                .build();

        CustomerWithAccount customerWithAccountMock = new CustomerWithAccount.Builder()
                .withId(1)
                .withCostumer(customerMock)
                .withAccount(accountMock)
                .withDebidCard(debitCardMock)
                .buildDebitCard();

        when(repositoryMock.findCustomerWithAccountThroughDebitCard(customerWithAccountMock.getDebitCard().getCardNumber())).thenReturn(Optional.of(customerWithAccountMock));
        final CustomerWithAccount customerWithAccountThroughCardNumber = new CustomerWithAccountService(repositoryMock, customerServiceMock, accountServiceMock, cardServiceMock, transactionServiceMock).findCustomerWithAccountThroughCardNumber(customerWithAccountMock.getDebitCard().getCardNumber());

        assert(customerWithAccountThroughCardNumber == customerWithAccountMock);
    }
}