package customerswithaccount.repository;

import common.repository.InMemRepository;
import customerswithaccount.model.CustomerWithAccount;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerWithAccountRepository extends InMemRepository<CustomerWithAccount> implements ICustomerWithAccountRepository {

    private final List<CustomerWithAccount> repository = new LinkedList<>();
    private static Integer customerWithAccountIdCounter = 0;

    private static int generateCustomerWithAccountId() {
        return ++customerWithAccountIdCounter;
    }
    @Override
    public Optional<CustomerWithAccount> create(CustomerWithAccount newItem) {
        if (newItem == null) {
            throw new IllegalArgumentException("CustomerWithAccount can not be null");
        }
        CustomerWithAccount customerWithAccount = newItem;
        customerWithAccount.setId(generateCustomerWithAccountId());
        repository.add(customerWithAccount);
        return Optional.of(customerWithAccount);
    }

    @Override
    public boolean deleteById(Integer id) {
        return repository.removeIf(customerWithAccount -> customerWithAccount.getId().equals(id));
    }

    @Override
    public Optional<CustomerWithAccount> update(CustomerWithAccount newItem) {
        if (deleteById(newItem.getId())) {
            repository.add(newItem);
            return Optional.of(newItem);
        }
        return Optional.empty();
    }

    public Optional<CustomerWithAccount> getById(Integer id) {
        for (CustomerWithAccount customerWithAccount : repository) {
            if (customerWithAccount.getId().equals(id)) {
                return Optional.of(customerWithAccount);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<CustomerWithAccount> getAll() {
        if (repository.isEmpty()){
            throw new IllegalArgumentException("CustomerWithAccount repository is empty");
        }
        return repository;
    }

    //We can only have one account for primary customer
    @Override
    public Optional<CustomerWithAccount> findCustomerWithAccountThroughAccountNumber(String accountNumber) {
        for (CustomerWithAccount customerWithAccount: repository) {
            if (customerWithAccount.getAccount().getAccountNumber().equals(accountNumber)) {
                return Optional.of(customerWithAccount);
            }
        }
        return Optional.empty();
    }

    //Customer can have several account with one tax id
    @Override
    public List<CustomerWithAccount> findAllCustomerWithAccountThroughTaxId(String customerTaxId) {
        return getAll().stream().filter(customerWithAccount -> customerWithAccount.getCustomer().getTaxId().equals(customerTaxId)).collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerWithAccount> findCustomerWithAccountThroughDebitCard(String debitCardNumber) {
        for (CustomerWithAccount customerWithAccount: repository) {
            if (customerWithAccount.getDebitCard() != null && customerWithAccount.getDebitCard().getCardNumber().equals(debitCardNumber)) {
                return Optional.of(customerWithAccount);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CustomerWithAccount> findCustomerWithAccountThroughCreditCard(String creditCardNumber) {
        for (CustomerWithAccount customerWithAccount:repository) {
            if (customerWithAccount.getCreditCard() != null && customerWithAccount.getCreditCard().getCardNumber().equals(creditCardNumber)) {
                return Optional.of(customerWithAccount);
            }
        }
        return Optional.empty();
    }

    public Optional<CustomerWithAccount> findCustomerWithAccountThroughCard(String cardNumber) {
        final Optional<CustomerWithAccount> customerWithAccountThroughDebitCard = findCustomerWithAccountThroughDebitCard(cardNumber);
        final Optional<CustomerWithAccount> customerWithAccountThroughCreditCard = findCustomerWithAccountThroughCreditCard(cardNumber);
        if (customerWithAccountThroughDebitCard.isEmpty()){
            return customerWithAccountThroughCreditCard;
        } else {
            return customerWithAccountThroughDebitCard;
        }
    }
}
