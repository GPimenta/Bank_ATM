package costumers.repository;

import costumers.model.Customer;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class InMemCustomerRepositoryImpl implements ICustomerRepository{
    private final Set<Customer> repository = new TreeSet<>();

    private static Integer customerIdCounter = 0;

    private static int generateCustomerId(){
        return ++customerIdCounter;
    }


    @Override
    public Optional<Customer> create(Customer newItem) {
        if (newItem == null){
            throw new IllegalArgumentException("Customer can not be null");
        }
        Customer customer = newItem;
        customer.setId(generateCustomerId());
        repository.add(customer); // change from newItem to customer
        return Optional.of(customer);
    }

    @Override
    public boolean deleteById(Integer id) {
        return repository.removeIf(customer -> customer.getId().equals(id));
    }

    @Override
    public Optional<Customer> update(Customer newItem) {
        if (deleteById(newItem.getId())){
            repository.add(newItem);
            return Optional.of(newItem);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> getById(Integer id) {
        for (Customer customer : repository){
            if (customer.getId().equals(id)){
                return Optional.of(customer);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Customer> getAll() {
        if (repository.isEmpty()){
            throw new IllegalArgumentException("Customer repository is empty");
        }
        return repository;
    }

    @Override
    public Optional<Customer> getByTaxId(String taxId) {
        for (Customer customer : repository){
            if (customer.getTaxId().equals(taxId)){
                return Optional.of(customer);
            }
        }
        return Optional.empty();
    }
}
