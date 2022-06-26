package accounts.service;

import accounts.exceptions.*;
import accounts.model.Account;

import java.util.Collection;

public interface IAccountService {

    Account createAccount(Integer customerId) throws AccountConflictException;

    void deleteAccount(Integer accountId) throws AccountNotFoundException;

    Account getAccount(Integer accountId) throws AccountNotFoundException;

    Account findAccountByHolderCustomerId(Integer customerId) throws AccountNotFoundException;

    Collection<Account> findAccountsBySecondaryCustomerId(Integer customerId);

    Collection<Account> findAllAccountByCustomerId(Integer customerId);

    void depositAccount(Integer accountId, Double amount) throws AccountConflictException, AccountNotFoundException, AccountVoidDepositException;

    void withdrawAccount(Integer accountId, Double amount) throws AccountConflictException, AccountNotFoundException, AccountVoidWithdrawException, AccountNoFundsException;

    void transferMoney(Integer fromAccount, Integer toAccount, Double amount) throws AccountConflictException, AccountNotFoundException, AccountVoidWithdrawException, AccountNoFundsException, AccountVoidDepositException;

    void addSecondaryOwner(Integer accountId, Integer customerId) throws AccountConflictException, AccountNotFoundException;

    void deleteSecondaryOwner(Integer AccountId, Integer customerId) throws AccountConflictException, AccountNotFoundException;


}
