package customerswithaccount.repository;

import customerswithaccount.model.CustomerWithAccount;

import java.util.Collection;
import java.util.Optional;

public class CustomerWithAccountRepository implements ICustomerWithAccountRepository {
    @Override
    public Optional<CustomerWithAccount> create(CustomerWithAccount newItem) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public Optional<CustomerWithAccount> update(CustomerWithAccount newItem) {
        return Optional.empty();
    }

    @Override
    public Optional<CustomerWithAccount> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Collection<CustomerWithAccount> getAll() {
        return null;
    }

    @Override
    public Optional<CustomerWithAccount> findByCustomerWithAccountId(Integer customerWithAccountId) {
        return Optional.empty();
    }
}
