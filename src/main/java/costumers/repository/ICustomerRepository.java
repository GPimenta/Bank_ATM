package costumers.repository;

import common.repository.IRepository;
import costumers.model.Customer;

import java.util.Optional;

public interface ICustomerRepository extends IRepository<Customer> {
    public Optional<Customer> getByTaxId(String taxId);
}
