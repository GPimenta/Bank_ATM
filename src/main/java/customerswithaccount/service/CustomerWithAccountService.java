package customerswithaccount.service;

import accounts.exceptions.AccountConflictException;
import accounts.model.Account;
import accounts.repository.IAccountRepository;
import accounts.service.AccountService;
import accounts.service.IAccountService;
import cards.exceptions.CardConflictException;
import cards.model.CreditCard;
import cards.model.DebitCard;
import cards.repository.ICardRepository;
import cards.service.CardService;
import cards.service.ICardService;
import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.model.Customer;
import costumers.repository.ICustomerRepository;
import costumers.service.CustomerService;
import costumers.service.ICustomerService;
import customerswithaccount.exceptions.CustomerWithAccountConflictException;
import customerswithaccount.exceptions.CustomerWithAccountNotFoundException;
import customerswithaccount.model.CustomerWithAccount;
import customerswithaccount.repository.ICustomerWithAccountRepository;
import utils.IPreconditions;

import java.time.LocalDate;
import java.util.List;

public class CustomerWithAccountService implements ICustomerWithAccountService {

    //MAYBE WE HAVE TO CHANGE TO SERVICES INSTEAD OF REPOSITORIES

    private final ICustomerWithAccountRepository repository;
    private final ICustomerService customerService;
    private final IAccountService accountService;
    private final ICardService cardService;



    public CustomerWithAccountService(ICustomerWithAccountRepository repository, ICustomerService customerService, IAccountService accountService, ICardService cardService) {
        this.repository = IPreconditions.checkNotNull(repository, "CustomerWithAccount Repository cannot be null");
        this.customerService = IPreconditions.checkNotNull(customerService, "Customer service cannot be null");
        this.accountService = IPreconditions.checkNotNull(accountService, "Account service cannot be null");
        this.cardService = IPreconditions.checkNotNull(cardService, "Card service cannot be null");
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

    public CustomerWithAccount findCustomerWithAccountThroughAccountNumber(String accountNumber) throws CustomerWithAccountNotFoundException {
        return repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with account number %i not found", accountNumber));
    }

    public List<CustomerWithAccount> findAllCustomerWithAccountThroughTaxId(String customerTaxId) {
        return repository.findAllCustomerWithAccountThroughTaxId(customerTaxId);
    }

    public CustomerWithAccount findCustomerWithAccountThroughCardNumber(String cardNumber) throws CustomerWithAccountNotFoundException {
        return repository.findCustomerWithAccountThroughDebitCard(cardNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with account number %i not found", cardNumber));
    }
}
