package costumers.service;

import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.model.Customer;
import costumers.repository.ICustomerRepository;
import costumers.repository.InMemCustomerRepositoryImpl;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    ICustomerRepository customerRepository = new InMemCustomerRepositoryImpl();
    ICustomerService customerService = new CustomerService(customerRepository);


    @org.junit.jupiter.api.Test
    void createCustomer() throws CustomerConflictException, CustomerNotFoundException {
        customerService.createCustomer("Goncalo", "259569038", "goncalo@gmail.com", LocalDate.of(1989, Month.AUGUST, 3));
        customerService.createCustomer("Nixx", "123456789", "nix@gmail.com", LocalDate.of(2010, Month.JUNE, 10));
        customerService.createCustomer("Leandro", "987654321", "leandro@gmail.com", LocalDate.of(2000, Month.JANUARY, 20));
        customerService.createCustomer("Barbichas", "192837465", "barbichas@gmail.com", LocalDate.of(1996, Month.MARCH, 12));

        Customer customer = new Customer.Builder()
                .withId(1)
                .withName("Goncalo")
                .withTaxId("259569038")
                .withEmail("goncalo@gmail.com")
                .withBirthday(LocalDate.of(1989, Month.AUGUST, 3))
                .build();


        assertEquals(customer, customerService.findByTaxId("259569038"));
    }

    @org.junit.jupiter.api.Test
    void findByTaxId() {
    }

    @org.junit.jupiter.api.Test
    void getCustomer() {
    }

    @org.junit.jupiter.api.Test
    void deleteCustomer() {
    }

    @org.junit.jupiter.api.Test
    void getAllCustomer() {
    }
}