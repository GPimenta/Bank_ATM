package accounts.service;

import accounts.exceptions.*;
import accounts.model.Account;
import accounts.repository.IAccountRepository;
import utils.INumbersGenerator;
import utils.IPreconditions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AccountService implements IAccountService {

    private final IAccountRepository repository;

    public AccountService(IAccountRepository repository) {
        this.repository = IPreconditions.checkNotNull(repository, "Account Repository cannot be null");
    }

    private static String generateAccountNumber() {
        return INumbersGenerator.createString(Account.ACCOUNT_NUMBER_LENGTH);
    }

    private static String generateAccountPassword() {
        return INumbersGenerator.createString(Account.ACCOUNT_PASSWORD_LENGTH);
    }

    @Override
    public Account createAccount(Integer customerId) throws AccountConflictException {
        Account account = new Account.Builder()
                .withCustomerId(customerId)
                .withAccountNumber(generateAccountNumber())
                .withBalance(0D)
                .withPasswordAccount(generateAccountPassword())
                .build();

        return repository.create(account)
                .orElseThrow(() -> new AccountConflictException("Conflict on creating account with customer Id: '%d'", customerId));
    }

    @Override
    public void deleteAccount(Integer accountId) throws AccountNotFoundException {
        if (!repository.deleteById(accountId)) {
            throw new AccountNotFoundException("Account with Id: '%d' not found to delete", accountId);
        }

    }

    @Override
    public Account getAccount(Integer accountId) throws AccountNotFoundException {
        return repository.getById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with Id: '%d' not found to get Account", accountId));
    }

    public Account updateAccount(String accountNumber, String newPassword, Double balance, List<Integer> secondaryOwners) throws AccountNotFoundException {
        final Account oldAccount = repository.findAccountByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account number %s not found"));

        oldAccount.setPassword(newPassword);
        oldAccount.setBalance(balance);
        oldAccount.setSecondaryOwnersId(secondaryOwners);
        return repository.update(oldAccount).orElseThrow(() -> new AccountNotFoundException("Account number %s not found", accountNumber));
    }
    @Override
    public Account findAccountByHolderCustomerId(Integer customerId) throws AccountNotFoundException {

        return repository.findByHolderCustomerId(customerId).orElseThrow(() -> new AccountNotFoundException("Account not found with customer Id: %d", customerId));
    }

    @Override
    public Collection<Account> findAccountsBySecondaryCustomerId(Integer customerId) {
        return repository.findBySecondaryCustomerId(customerId);
    }

    @Override
    public Collection<Account> findAllAccountByCustomerId(Integer customerId) {
        if (Objects.isNull(customerId) || customerId == 0) {
            return Collections.emptyList();
        }
        return repository.findByAllCustomerId(customerId);
    }

    @Override
    public void depositAccount(Integer accountId, Double amount) throws AccountConflictException, AccountNotFoundException, AccountVoidDepositException {
        if (amount <= 0) {
            throw new AccountVoidDepositException("Cannot deposit with %.2f", amount);
        }
        Account account = getAccount(accountId);
        account.setBalance(account.getBalance() + amount);
        repository.update(account);

    }

    @Override
    public void withdrawAccount(Integer accountId, Double amount) throws AccountConflictException, AccountNotFoundException, AccountVoidWithdrawException, AccountNoFundsException {
        if (amount <= 0) {
            throw new AccountVoidWithdrawException("Cannot deposit with '%.2f", amount);
        }
        Account account = getAccount(accountId);

        if (account.getBalance() == 0){
            throw new AccountNotFoundException("There is no funds on the account Id: '%.2f' , in order to withdraw", accountId);
        }
        if (account.getBalance() - amount < 0){
            throw new AccountNoFundsException("Not enough funds to withdraw '%.2f'", amount);
        }

        account.setBalance(account.getBalance() - amount);

        repository.update(account);

    }

    @Override
    public void transferMoney(Integer fromAccount, Integer toAccount, Double amount) throws AccountConflictException, AccountNotFoundException, AccountVoidWithdrawException, AccountNoFundsException, AccountVoidDepositException {
        Account withdrawFrom = getAccount(fromAccount);
        Account depositTo = getAccount(toAccount);

        if (fromAccount.equals(toAccount)){
            throw new AccountConflictException("Cannot transfer money, from AccountId: '%d' to the same AccountId: '%d'", fromAccount, toAccount);
        }
        withdrawAccount(fromAccount, amount);
        depositAccount(toAccount, amount);
    }

    @Override
    public void addSecondaryOwner(Integer accountId, Integer customerId) throws AccountConflictException, AccountNotFoundException {
        Account account = getAccount(accountId);
        List<Integer> secondaryOwners = account.getSecondaryOwnersId();

        if (account.getCustomerId() == customerId){
            throw new AccountConflictException("account with ID '%s' already has customer with ID '%s' as primary owner", accountId, customerId);
        }
        if (secondaryOwners.size() >= Account.MAX_NUMBER_SECONDARY_OWNERS){
            throw new AccountConflictException("The account with Id '%d' has exceed the number of secondary Owners, which is: '%d'", accountId, Account.MAX_NUMBER_SECONDARY_OWNERS);
        }
        if (secondaryOwners.contains(customerId)){
            throw new AccountConflictException("The customer with Id '%d' is already on the secondary Owner list of this account Id '%d'", customerId, accountId);
        }
        account.getSecondaryOwnersId().add(customerId);
        repository.update(account);

    }

    @Override
    public void deleteSecondaryOwner(Integer accountId, Integer customerId) throws AccountConflictException, AccountNotFoundException {
        Account account = getAccount(accountId);
        Collection<Integer> secondaryOwners = account.getSecondaryOwnersId();

        if (account.getCustomerId() == customerId){
            throw new AccountConflictException("account with Id '%d' already has customer with Id '%d' as primary owner", accountId, customerId);
        }
        if (secondaryOwners.isEmpty()){
            throw new AccountConflictException("The account with Id '%d' is empty", accountId);
        }
        if (!secondaryOwners.remove(customerId)){
            throw new AccountNotFoundException("Customer with Id: '%d' not found, on secondaryOwner List", customerId);
        }
        account.getSecondaryOwnersId().remove(customerId);
        repository.update(account);
    }
}
