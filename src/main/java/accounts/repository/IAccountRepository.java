package accounts.repository;

import accounts.model.Account;
import common.repository.IRepository;

import java.util.Collection;
import java.util.Optional;

public interface IAccountRepository extends IRepository<Account> {
    Optional<Account> findByHolderCustomerId(Integer customerId);
    Collection<Account> findBySecondaryCustomerId(Integer customerId);
    Collection<Account> findByAllCustomerId(Integer customerId);

}
