package customerswithaccount.service;

import accounts.exceptions.*;
import accounts.model.Account;
import accounts.service.IAccountService;
import cards.exceptions.CardConflictException;
import cards.exceptions.CardNotFoundException;
import cards.model.Card;
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
import transaction.model.Transaction;
import transaction.service.ITransactionService;
import utils.IPreconditions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        return repository.create(customerWithAccount).orElseThrow(() -> new CustomerWithAccountConflictException("Conflict on creating customer %s with account number %s", name, account.getAccountNumber()));
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

        return repository.create(customerWithAccount).orElseThrow(() -> new CustomerWithAccountConflictException("Conflict on creating customer %s with account number %s and debit card number %s", name, account.getAccountNumber(), creditCard.getCardNumber()));
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

    @Override
    public boolean deleteCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CustomerNotFoundException, AccountNotFoundException, CardNotFoundException {
        final CustomerWithAccount customerWithAccount = findCustomerWithAccountThroughAccountNumber(accountNumber);
        customerService.deleteCustomer(customerWithAccount.getCustomer().getId());
        accountService.deleteAccount(customerWithAccount.getAccount().getId());

        if (customerWithAccount.getDebitCard() != null) {
            cardService.deleteCard(customerWithAccount.getDebitCard().getId());
        }
        if (customerWithAccount.getCreditCard() != null) {
            cardService.deleteCard(customerWithAccount.getCreditCard().getId());
        }

        return repository.deleteById(customerWithAccount.getId());
    }


    @Override
    public CustomerWithAccount addDebitCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardConflictException {
        final CustomerWithAccount customerWithAccount = repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with Account number %s was not found", accountNumber));
        customerWithAccount.setDebitCard(cardService.createDebitCard(customerWithAccount.getAccount().getId(), customerWithAccount.getCustomer().getId()));

        return repository.update(customerWithAccount).orElseThrow(() -> new CustomerWithAccountNotFoundException(" Customer with account number %s not found", accountNumber));
    }

    @Override
    public void deleteDebitCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardNotFoundException {
        final CustomerWithAccount customerWithAccount = repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with Account number %s was not found", accountNumber));
        cardService.deleteCard(customerWithAccount.getDebitCard().getId());
    }

    @Override
    public CustomerWithAccount addCreditCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardConflictException {
        final CustomerWithAccount customerWithAccount = repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with Account number %s was not found", accountNumber));
        customerWithAccount.setCreditCard(cardService.createCreditCard(customerWithAccount.getAccount().getId(), customerWithAccount.getCustomer().getId()));

        return repository.update(customerWithAccount).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with account number %s not found", accountNumber));
    }

    @Override
    public void deleteCreditCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardNotFoundException {
        final CustomerWithAccount customerWithAccount = repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with Account number %s was not found", accountNumber));
        cardService.deleteCard(customerWithAccount.getCreditCard().getId());
    }
    @Override
    public void deleteAllCards(String accountNumber) throws CustomerWithAccountNotFoundException, CardNotFoundException {
        final CustomerWithAccount customerWithAccount = repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with Account number %s was not found", accountNumber));
        cardService.deleteCard(customerWithAccount.getDebitCard().getId());
        cardService.deleteCard(customerWithAccount.getCreditCard().getId());
    }

//Check if the customer already has debit or credit card
    @Override
    public CustomerWithAccount addCardsToCustomerWithAccount(String accountNumber) throws CardConflictException, CustomerWithAccountNotFoundException {
        final CustomerWithAccount customerWithAccount = repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with Account number %s was not found", accountNumber));
        customerWithAccount.setDebitCard(cardService.createDebitCard(customerWithAccount.getAccount().getId(), customerWithAccount.getCustomer().getId()));
        customerWithAccount.setCreditCard(cardService.createCreditCard(customerWithAccount.getAccount().getId(), customerWithAccount.getCustomer().getId()));

        return repository.update(customerWithAccount).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with account number %s not found", accountNumber));
    }

    //Need to return transacation object. But first we need to change the transaction repo and service to give a transaction without a destination account
    @Override
    public boolean withdrawMoneyWithCard(String cardNumber, String pin, Double amount) throws CustomerWithAccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountConflictException, AccountNotFoundException {
         if (cardService.checkCardNumberAndPassword(cardNumber, pin)) {
             accountService.withdrawAccount(findCustomerWithAccountThroughCardNumber(cardNumber).getAccount().getId(), amount);
             return true;
         } else {
             return false;
         }
    }

    @Override
    public void depositMoney(Integer accountId, Double amount) throws AccountVoidDepositException, AccountConflictException, AccountNotFoundException {
        accountService.depositAccount(accountId, amount);
    }

    @Override
    public boolean depositMoneyWithCard(String cardNumber, String pin, Double amount) throws CustomerWithAccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountConflictException, AccountNotFoundException, AccountVoidDepositException {
        if (cardService.checkCardNumberAndPassword(cardNumber, pin)) {
            accountService.depositAccount(findCustomerWithAccountThroughCardNumber(cardNumber).getAccount().getId(), amount);
            return true;
        } else {
            return false;
        }
    }

    //This function can be revamp to add the functions above
    @Override
    public boolean transferMoneyWithCard (String cardNumber, String pin, String accountNumber, Double amount) throws CustomerWithAccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountVoidDepositException, AccountConflictException, AccountNotFoundException, TransactonConflictException {
        if (cardService.checkCardNumberAndPassword(cardNumber, pin)) {
            final Integer fromAccountId = findCustomerWithAccountThroughCardNumber(cardNumber).getAccount().getId();
            final Integer toAccountId = findCustomerWithAccountThroughAccountNumber(accountNumber).getAccount().getId();
            final Integer cardId = cardService.getCardByCardNumber(cardNumber).getId();

            if (accountService.transferMoney(fromAccountId, toAccountId, amount) != null ){
                transactionService.createTransaction(fromAccountId, toAccountId, cardId, LocalDateTime.now(), String.valueOf(amount));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addSecondaryOwners(String primaryAccountOwner, String secondaryCustomerTaxIdOwner) throws CustomerWithAccountNotFoundException, CustomerNotFoundException, AccountConflictException, AccountNotFoundException {
        final CustomerWithAccount primaryCustomerWithAccount = findCustomerWithAccountThroughAccountNumber(primaryAccountOwner);
        return accountService.addSecondaryOwner(primaryCustomerWithAccount.getAccount().getId(), customerService.findByTaxId(secondaryCustomerTaxIdOwner).getId()) != null;
    }

    @Override
    public boolean deleteSecondaryOwners(String primaryAccountOwner, String secondaryCustomerTaxIdOwner) throws CustomerWithAccountNotFoundException, CustomerNotFoundException, AccountConflictException, AccountNotFoundException {
        final CustomerWithAccount primaryCustomerWithAccount = findCustomerWithAccountThroughAccountNumber(primaryAccountOwner);
        return accountService.deleteSecondaryOwner(primaryCustomerWithAccount.getAccount().getId(), customerService.findByTaxId(secondaryCustomerTaxIdOwner).getId()) != null;
    }

    @Override
    public CustomerWithAccount findCustomerWithAccountThroughAccountNumber(String accountNumber) throws CustomerWithAccountNotFoundException {
        return repository.findCustomerWithAccountThroughAccountNumber(accountNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with account number %s not found", accountNumber));
    }

    @Override
    public List<CustomerWithAccount> findAllCustomerWithAccountThroughTaxId(String customerTaxId) {
        return repository.findAllCustomerWithAccountThroughTaxId(customerTaxId);
    }

    @Override
    public CustomerWithAccount findCustomerWithAccountThroughCardNumber(String cardNumber) throws CustomerWithAccountNotFoundException {
        final Optional<CustomerWithAccount> customerWithDebitCard = repository.findCustomerWithAccountThroughDebitCard(cardNumber);
        if (customerWithDebitCard.isEmpty()) {
            return repository.findCustomerWithAccountThroughCreditCard(cardNumber).orElseThrow(() -> new CustomerWithAccountNotFoundException("Customer with account number %s not found", cardNumber));
        }
        return customerWithDebitCard.get();
    }

    @Override
    public void showAllCustomersWithAccount() {
        repository.getAll().forEach(System.out::println);
    }
}
