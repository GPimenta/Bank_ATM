package customerswithaccount.repository;

import common.repository.IRepository;
import customerswithaccount.model.CustomerWithAccount;

import java.util.Optional;

public interface ICustomerWithAccountRepository extends IRepository<CustomerWithAccount> {

    Optional<CustomerWithAccount> findByCustomerWithAccountId(Integer customerWithAccountId);


}
