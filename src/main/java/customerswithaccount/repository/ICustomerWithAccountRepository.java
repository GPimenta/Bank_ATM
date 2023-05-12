package customerswithaccount.repository;

import common.repository.IRepository;
import customerswithaccount.model.CustomerWithAccount;

import java.util.List;
import java.util.Optional;

public interface ICustomerWithAccountRepository extends IRepository<CustomerWithAccount> {

    Optional<CustomerWithAccount> findCustomerWithAccountThroughAccountNumber(String accountNumber);
    List<CustomerWithAccount> findAllCustomerWithAccountThroughTaxId(String customerTaxId);
    Optional<CustomerWithAccount> findCustomerWithAccountThroughDebitCard(String debitCardNumber);
    Optional<CustomerWithAccount> findCustomerWithAccountThroughCreditCard(String creditCardNumber);

}
