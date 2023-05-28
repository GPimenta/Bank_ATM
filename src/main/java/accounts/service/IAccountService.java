package accounts.service;

import accounts.exceptions.*;
import accounts.model.Account;

import java.util.Collection;
import java.util.List;

public interface IAccountService {

    Account createAccount(Integer customerId) throws AccountConflictException;

    void deleteAccount(Integer accountId) throws AccountNotFoundException;

    Account getAccount(Integer accountId) throws AccountNotFoundException;

    public Account updateAccount(String accountNumber, String newPassword, Double balance, List<Integer> secondaryOwners) throws AccountNotFoundException;

    Account findAccountByHolderCustomerId(Integer customerId) throws AccountNotFoundException;

    Collection<Account> findAccountsBySecondaryCustomerId(Integer customerId);

    Collection<Account> findAllAccountByCustomerId(Integer customerId);

    Account depositAccount(Integer accountId, Double amount) throws AccountConflictException, AccountNotFoundException, AccountVoidDepositException;

    Account withdrawAccount(Integer accountId, Double amount) throws AccountConflictException, AccountNotFoundException, AccountVoidWithdrawException, AccountNoFundsException;

    Account transferMoney(Integer fromAccount, Integer toAccount, Double amount) throws AccountConflictException, AccountNotFoundException, AccountVoidWithdrawException, AccountNoFundsException, AccountVoidDepositException;

    Account addSecondaryOwner(Integer accountId, Integer customerId) throws AccountConflictException, AccountNotFoundException;

    Account deleteSecondaryOwner(Integer AccountId, Integer customerId) throws AccountConflictException, AccountNotFoundException;


}
