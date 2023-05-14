package customerswithaccount.service;

import accounts.exceptions.*;
import accounts.model.Account;
import accounts.service.IAccountService;
import cards.exceptions.CardConflictException;
import cards.model.CreditCard;
import cards.model.DebitCard;
import cards.service.ICardService;
import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.model.Customer;
import costumers.service.ICustomerService;
import customerswithaccount.exceptions.CustomerWithAccountConflictException;
import customerswithaccount.exceptions.CustomerWithAccountNotFoundException;
import customerswithaccount.model.CustomerWithAccount;
import customerswithaccount.repository.ICustomerWithAccountRepository;
import transaction.exceptions.TransactonConflictException;
import transaction.service.ITransactionService;
import utils.IPreconditions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerWithAccountService implements ICustomerWithAccountService {

    private final ICustomerWithAccountRepository repository;
    private final ICustomerService customerService;
    private final IAccountService accountService;
    private final ICardService cardService;
    private final ITransactionService transactionService;



    public CustomerWithAccountService(ICustomerWithAccountRepository repository, ICustomerService customerService, IAccountService accountService, ICardService cardService, ITransactionService transactionService) {
        this.repository = IPreconditions.checkNotNull(repository, "CustomerWithAccount Repository cannot be null");
        this.customerService = IPreconditions.checkNotNull(customerService, "Customer service cannot be null");
        this.accountService = IPreconditions.checkNotNull(accountService, "Account service cannot be null");
        this.cardService = IPreconditions.checkNotNull(cardService, "Card service cannot be null");
        this.transactionService = IPreconditions.checkNotNull(transactionService, "Transaction service cannot be null");
    }

    @Override
    public CustomerWithAccount createCustomerWithAccount(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException {
        final Customer customer = customerService.createCustomer(name, taxId, email, birthday);
        final Account account = accountService.createAccount(customer.getId());

        CustomerWithAccount customerWithAccount = new CustomerWithAccount.Builder()
                .withCostumer(customer)
                .withAccount(account)
                .build();

        return repository.create(customerWithAccount).orElseThrow(() -> new CustomerWithAccountConflictException("Conflict on creating customer %s with account number %i", name, account.getAccountNumber()));
    }

    @Override
    public CustomerWithAccount createCustomerWithAccountDebitCard(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException {
        final Customer customer = customerService.createCustomer(name, taxId, email, birthday);
        final Account account = accountService.createAccount(customer.getId());
        final DebitCard debitCard = cardService.createDebitCard(account.getId(), customer.getId());

        CustomerWithAccount customerWithAccount = new CustomerWithAccount.Builder()
                .withCostumer(customer)
                .withAccount(account)
                .withDebidCard(debitCard)
                .buildDebitCard();

        return repository.create(customerWithAccount).orElseThrow(() -> new CustomerWithAccountConflictException("Conflict on creating customer %s with account number %i and debit card number %i", name, account.getAccountNumber(), debitCard.getCardNumber()));
    }

    @Override
    public CustomerWithAccount createCustomerWithAccountCreditCard(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException {
        final Customer customer = customerService.createCustomer(name, taxId, email, birthday);
        final Account account = accountService.createAccount(customer.getId());
        final CreditCard creditCard = cardService.createCreditCard(account.getId(), customer.getId());

        CustomerWithAccount customerWithAccount = new CustomerWithAccount.Builder()
                .withCostumer(customer)
                .withAccount(account)
                .withCreditCard(creditCard)
                .buildCreditCard();

        return repository.create(customerWithAccount).orElseThrow(() -> new CustomerWithAccountConflictException("Conflict on creating customer %s with account number %i and debit card number %i", name, account.getAccountNumber(), creditCard.getCardNumber()));
    }

    @Override
    public CustomerWithAccount createCustomerWithAccountCards(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException {
        final Customer customer = customerService.createCustomer(name, taxId, email, birthday);
        final Account account = accountService.createAccount(customer.getId());
        final DebitCard debitCard = cardService.createDebitCard(account.getId(), customer.getId());
        final CreditCard creditCard = cardService.createCreditCard(account.getId(), customer.getId());

        CustomerWithAccount customerWithAccount = new CustomerWithAccount.Builder()
                .withCostumer(customer)
                .withAccount(account)
                .withDebidCard(debitCard)
                .withCreditCard(creditCard)
                .buildAllCard();

        return repository.create(customerWithAccount).orElseThrow(() -> new CustomerWithAccountConflictException("Conflict on creating customer %s with account number %i, debit card number %i and credit card number %i", name, account.getAccountNumber(),debitCard.getCardNumber(), creditCard.getCardNumber()));
    }


    public void deleteCustomerWithAccount(Integer customerWithAccountId) throws CustomerWithAccountNotFoundException {
        if (!repository.deleteById(customerWithAccountId)) {
            throw new CustomerWithAccountNotFoundException("CustomerWithAccount with Id %i not found to delete", customerWithAccountId);
        }
    }

    public CustomerWithAccount addDebitCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardConflictException {
        final CustomerWithAccount customerWithAccount = repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with Account number %s was not found", accountNumber));
        customerWithAccount.setDebitCard(cardService.createDebitCard(customerWithAccount.getAccount().getId(), customerWithAccount.getCustomer().getId()));

        return customerWithAccount;
    }

    public CustomerWithAccount addCreditCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardConflictException {
        final CustomerWithAccount customerWithAccount = repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with Account number %s was not found", accountNumber));
        customerWithAccount.setCreditCard(cardService.createCreditCard(customerWithAccount.getAccount().getId(), customerWithAccount.getCustomer().getId()));

        return customerWithAccount;
    }

    public CustomerWithAccount addCardsToCustomerWithAccount(String accountNumber) throws CardConflictException, CustomerWithAccountNotFoundException {
        addCreditCardToCustomerWithAccount(accountNumber);
        return addDebitCardToCustomerWithAccount(accountNumber);
    }


    private void withdrawMoney(CustomerWithAccount customerWithAccount, Double amount) throws AccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountConflictException {
        accountService.withdrawAccount(customerWithAccount.getAccount().getId(),amount);
    }

    private void depositMoney(CustomerWithAccount customerWithAccount, Double amount) throws AccountNoFundsException, AccountVoidWithdrawException, AccountConflictException, AccountNotFoundException {
        accountService.withdrawAccount(customerWithAccount.getAccount().getId(), amount);
    }

    public boolean withdrawMoneyWithCard(String cardNumber, String pin, Double amount) throws CustomerWithAccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountConflictException, AccountNotFoundException {
        if (cardService.checkCardNumberAndPassword(cardNumber, pin)) {
            depositMoney(findCustomerWithAccountThroughCardNumber(cardNumber), amount);
            return true;
        } else {
            return false;
        }
    }

    public boolean transferMoneyWithCard (String cardNumber, String pin, String accountNumber, Double amount) throws CustomerWithAccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountVoidDepositException, AccountConflictException, AccountNotFoundException, TransactonConflictException {
        if (cardService.checkCardNumberAndPassword(cardNumber, pin)) {
            final Integer fromAccountId = findCustomerWithAccountThroughCardNumber(cardNumber).getAccount().getId();
            final Integer toAccountId = findCustomerWithAccountThroughAccountNumber(accountNumber).getAccount().getId();
            final Integer cardId = cardService.getCardByCardNumber(cardNumber).getId();

            accountService.transferMoney(fromAccountId, toAccountId, amount);
            transactionService.createTransaction(fromAccountId, toAccountId, cardId, LocalDateTime.now(), String.valueOf(amount));

            return true;
        } else {
            return false;
        }
    }

    public void addSecondaryOwners(String primaryAccountOwner, String secondaryCustomerTaxIdOwner) throws CustomerWithAccountNotFoundException, CustomerNotFoundException, AccountConflictException, AccountNotFoundException {
        final CustomerWithAccount primaryCustomerWithAccount = findCustomerWithAccountThroughAccountNumber(primaryAccountOwner);
        accountService.addSecondaryOwner(primaryCustomerWithAccount.getAccount().getId(), customerService.findByTaxId(secondaryCustomerTaxIdOwner).getId());
    }

    public void deleteSecondaryOwners(String primaryAccountOwner, String secondaryCustomerTaxIdOwner) throws CustomerWithAccountNotFoundException, CustomerNotFoundException, AccountConflictException, AccountNotFoundException {
        final CustomerWithAccount primaryCustomerWithAccount = findCustomerWithAccountThroughAccountNumber(primaryAccountOwner);
        accountService.deleteSecondaryOwner(primaryCustomerWithAccount.getAccount().getId(), customerService.findByTaxId(secondaryCustomerTaxIdOwner).getId());
    }

    public CustomerWithAccount findCustomerWithAccountThroughAccountNumber(String accountNumber) throws CustomerWithAccountNotFoundException {
        return repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with account number %i not found", accountNumber));
    }

    public List<CustomerWithAccount> findAllCustomerWithAccountThroughTaxId(String customerTaxId) {
        return repository.findAllCustomerWithAccountThroughTaxId(customerTaxId);
    }

    public CustomerWithAccount findCustomerWithAccountThroughCardNumber(String cardNumber) throws CustomerWithAccountNotFoundException {
        return repository.findCustomerWithAccountThroughDebitCard(cardNumber).orElse(repository.findCustomerWithAccountThroughCreditCard(cardNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with account number %i not found", cardNumber)));
    }
}
