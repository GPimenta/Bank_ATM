package customerswithaccount.service;

import accounts.exceptions.AccountConflictException;
import accounts.model.Account;
import cards.exceptions.CardConflictException;
import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.model.Customer;
import customerswithaccount.model.CustomerWithAccount;

import java.time.LocalDate;

public interface ICustomerWithAccountService {


    CustomerWithAccount createCustomerWithAccount(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException;

    CustomerWithAccount createCustomerWithAccountDebitCard(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException;

    CustomerWithAccount createCustomerWithAccountCreditCard(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException;

    CustomerWithAccount createCustomerWithAccountCards(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException, AccountConflictException, CardConflictException;



}
