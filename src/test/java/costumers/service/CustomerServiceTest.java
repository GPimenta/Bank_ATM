package costumers.service;

import costumers.exceptions.CustomerConflictException;
import costumers.exceptions.CustomerNotFoundException;
import costumers.model.Customer;
import costumers.repository.ICustomerRepository;
import costumers.repository.InMemCustomerRepositoryImpl;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

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
//

        assertEquals(customer, customerRepository.getById(1).get());
    }

    @org.junit.jupiter.api.Test
    void findByTaxId() throws CustomerConflictException, CustomerNotFoundException {
        customerService.createCustomer("Goncalo", "259569038", "goncalo@gmail.com", LocalDate.of(1989, Month.AUGUST, 3));

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
    void getCustomer() throws CustomerConflictException, CustomerNotFoundException {
        customerService.createCustomer("Goncalo", "259569038", "goncalo@gmail.com", LocalDate.of(1989, Month.AUGUST, 3));

        Customer customer = new Customer.Builder()
                .withId(1)
                .withName("Goncalo")
                .withTaxId("259569038")
                .withEmail("goncalo@gmail.com")
                .withBirthday(LocalDate.of(1989, Month.AUGUST, 3))
                .build();

        assertEquals(customer, customerService.getCustomer(1));
    }

    @org.junit.jupiter.api.Test
    void deleteCustomer() throws CustomerConflictException, CustomerNotFoundException {
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

        customerService.deleteCustomer(1);

        assertEquals(Optional.empty(), customerRepository.getById(1));



    }

    @org.junit.jupiter.api.Test
    void getAllCustomer() throws CustomerConflictException, CustomerNotFoundException {
        customerService.createCustomer("Goncalo", "259569038", "goncalo@gmail.com", LocalDate.of(1989, Month.AUGUST, 3));
        customerService.createCustomer("Nixx", "123456789", "nix@gmail.com", LocalDate.of(2010, Month.JUNE, 10));
        customerService.createCustomer("Leandro", "987654321", "leandro@gmail.com", LocalDate.of(2000, Month.JANUARY, 20));
        customerService.createCustomer("Barbichas", "192837465", "barbichas@gmail.com", LocalDate.of(1996, Month.MARCH, 12));

        Customer customer1 = new Customer.Builder()
                .withId(1)
                .withName("Goncalo")
                .withTaxId("259569038")
                .withEmail("goncalo@gmail.com")
                .withBirthday(LocalDate.of(1989, Month.AUGUST, 3))
                .build();

        Customer customer2 = new Customer.Builder()
                .withId(2)
                .withName("Nixx")
                .withTaxId("123456789")
                .withEmail("nix@gmail.com")
                .withBirthday(LocalDate.of(2010, Month.JUNE, 10))
                .build();

        Customer customer3 = new Customer.Builder()
                .withId(3)
                .withName("Leandro")
                .withTaxId("987654321")
                .withEmail("leandro@gmail.com")
                .withBirthday(LocalDate.of(2000, Month.JANUARY, 20))
                .build();

        Customer customer4 = new Customer.Builder()
                .withId(4)
                .withName("Barbichas")
                .withTaxId("192837465")
                .withEmail("barbichas@gmail.com")
                .withBirthday(LocalDate.of(1996, Month.MARCH, 12))
                .build();



        Collection<Customer> testListCustomer = new TreeSet<>();
        testListCustomer.add(customer1);
        testListCustomer.add(customer2);
        testListCustomer.add(customer3);
        testListCustomer.add(customer4);

        assertIterableEquals(testListCustomer, customerService.getAllCustomer());


    }
}