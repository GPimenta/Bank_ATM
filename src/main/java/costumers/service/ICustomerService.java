package costumers.service;

import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.model.Customer;

import java.time.LocalDate;
import java.util.Collection;

public interface ICustomerService {

    Customer createCustomer(String name, String taxId, String email, LocalDate birthday) throws CustomerConflictException, CustomerNotFoundException;

    Customer findByTaxId(String taxId) throws CustomerNotFoundException;

    Customer getCustomer(Integer customerId) throws CustomerNotFoundException;

    void deleteCustomer(Integer customerId) throws CustomerNotFoundException;

    Collection<Customer> getAllCustomer() throws CustomerNotFoundException;
}
