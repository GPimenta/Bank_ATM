package customerswithaccount.service;

import accounts.exceptions.*;
import accounts.model.Account;
import cards.exceptions.CardConflictException;
import cards.exceptions.CardNotFoundException;
import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.model.Customer;
import customerswithaccount.exceptions.CustomerWithAccountNotFoundException;
import customerswithaccount.model.CustomerWithAccount;
import transaction.exceptions.TransactonConflictException;

import java.time.LocalDate;
import java.util.List;

public interface ICustomerWithAccountService {


    CustomerWithAccount createCustomerWithAccount(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException;
    CustomerWithAccount createCustomerWithAccountDebitCard(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException;
    CustomerWithAccount createCustomerWithAccountCreditCard(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException;
    CustomerWithAccount createCustomerWithAccountCards(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException;
    boolean deleteCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CustomerNotFoundException, AccountNotFoundException, CardNotFoundException;
    CustomerWithAccount addDebitCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardConflictException;
    CustomerWithAccount addCreditCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardConflictException;
    CustomerWithAccount addCardsToCustomerWithAccount(String accountNumber) throws CardConflictException, CustomerWithAccountNotFoundException;
    boolean withdrawMoneyWithCard(String cardNumber, String pin, Double amount) throws CustomerWithAccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountConflictException, AccountNotFoundException;
    boolean depositMoneyWithCard(String cardNumber, String pin, Double amount) throws CustomerWithAccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountConflictException, AccountNotFoundException, AccountVoidDepositException;
    boolean transferMoneyWithCard (String cardNumber, String pin, String accountNumber, Double amount) throws CustomerWithAccountNotFoundException, AccountNoFundsException, AccountVoidWithdrawException, AccountVoidDepositException, AccountConflictException, AccountNotFoundException, TransactonConflictException;
    boolean addSecondaryOwners(String primaryAccountOwner, String secondaryCustomerTaxIdOwner) throws CustomerWithAccountNotFoundException, CustomerNotFoundException, AccountConflictException, AccountNotFoundException;
    boolean deleteSecondaryOwners(String primaryAccountOwner, String secondaryCustomerTaxIdOwner) throws CustomerWithAccountNotFoundException, CustomerNotFoundException, AccountConflictException, AccountNotFoundException;
    CustomerWithAccount findCustomerWithAccountThroughAccountNumber(String accountNumber) throws CustomerWithAccountNotFoundException;
    List<CustomerWithAccount> findAllCustomerWithAccountThroughTaxId(String customerTaxId);
    CustomerWithAccount findCustomerWithAccountThroughCardNumber(String cardNumber) throws CustomerWithAccountNotFoundException;
    void showAllCustomersWithAccount();
    void depositMoney(Integer accountId, Double amount) throws AccountVoidDepositException, AccountConflictException, AccountNotFoundException;
    CustomerWithAccount deleteDebitCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardNotFoundException;
    CustomerWithAccount deleteCreditCardToCustomerWithAccount(String accountNumber) throws CustomerWithAccountNotFoundException, CardNotFoundException;
    CustomerWithAccount deleteAllCards(String accountNumber) throws CustomerWithAccountNotFoundException, CardNotFoundException;
}
