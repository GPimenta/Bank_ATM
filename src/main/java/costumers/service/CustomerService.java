package costumers.service;

import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.model.Customer;
import costumers.repository.ICustomerRepository;
import utils.IPreconditions;

import java.time.LocalDate;
import java.util.Collection;

public class CustomerService implements ICustomerService{

    private final ICustomerRepository repository;

    public CustomerService(ICustomerRepository repository) {
        this.repository = IPreconditions.checkNotNull(repository, "Customer Repository cannot be null");
    }

    @Override
    public Customer createCustomer(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException {
        Customer customer = new Customer.Builder()
                .withName(name)
                .withTaxId(taxId)
                .withEmail(email)
                .withBirthday(birthday)
                .build();
        if (repository.getByTaxId(taxId).isPresent()){
            throw new CustomerNotFoundException("Customer cannot be created "
                    + "since there is already a Customer with taxId '%s' given", taxId);
        }

        return repository.create(customer).orElseThrow(() -> new CustomerConflictException("Conflict on creating "
                + "account with customer Id: '%i'", customer.getId()));
    }

    @Override
    public Customer findByTaxId(String taxId) throws CustomerNotFoundException {
        return repository.getByTaxId(taxId).orElseThrow(() -> new CustomerNotFoundException("Customer with taxId '%s' cannot be found", taxId));
    }

    @Override
    public Customer getCustomer(Integer customerId) throws CustomerNotFoundException {
        return repository.getById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer with Id '%s' cannot be found", customerId));
    }

    @Override
    public void deleteCustomer(Integer customerId) throws CustomerNotFoundException {
        if (!repository.deleteById(customerId)){
            throw new CustomerNotFoundException("Customer with Id: '%d' cannot be found", customerId);
        }
    }

    @Override
    public Collection<Customer> getAllCustomer() throws CustomerNotFoundException {
        return repository.getAll();
    }
}
