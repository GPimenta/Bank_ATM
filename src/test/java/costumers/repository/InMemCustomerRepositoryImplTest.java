package costumers.repository;

import costumers.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.INumbersGenerator;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemCustomerRepositoryImplTest {

    private static String generateTaxId(){
        return INumbersGenerator.createString(9);
    }

    ICustomerRepository customerRepository = new InMemCustomerRepositoryImpl();

    Customer customer1 = new Customer.Builder()
            .withName("Lanister")
            .withEmail("lanister@email.com")
            .withTaxId(generateTaxId())
            .withBirthday(LocalDate.of(1989, 1, 1))
            .build();

    Customer customer2 = new Customer.Builder()
            .withName("Cersei")
            .withEmail("cersei@email.com")
            .withTaxId(generateTaxId())
            .withBirthday(LocalDate.of(1980, 12, 7))
            .build();

    Customer customer3 = new Customer.Builder()
            .withName("Sansa")
            .withEmail("sansa@email.com")
            .withTaxId(generateTaxId())
            .withBirthday(LocalDate.of(2000, 8, 3))
            .build();

    @BeforeEach
    void setup() {
        customerRepository.create(customer1);
        customerRepository.create(customer2);
        customerRepository.create(customer3);
    }
    @AfterEach
    void end() {
        customerRepository.getAll().clear();
    }

    @Test
    void create() {
        assertEquals(customer1, customerRepository.getAll().stream().filter(customer -> customer.equals(customer1)).findAny().get());
    }

    @Test
    void deleteById() {
        customerRepository.deleteById(2);

        assertEquals(2, customerRepository.getAll().size());
        assertEquals(customer1, customerRepository.getAll().stream().filter(customer -> customer.equals(customer1)).findAny().get());
        assertEquals(customer3, customerRepository.getAll().stream().filter(customer -> customer.equals(customer3)).findAny().get());

    }

    @Test
    void update() {
        Customer customerTest = customer1;
        final String taxIdCustomer1 = customer1.getTaxId();

        customerTest.setName("Pikachu");
        customerRepository.update(customerTest);
        assertEquals(customerTest, customerRepository.getAll().stream().filter(customer -> customer.getTaxId() == taxIdCustomer1).findFirst().get());
    }

    @Test
    void getById() {
        assertEquals(customer1, customerRepository.getById(1).get());
    }

    @Test
    void getAll() {
        assertEquals(3, customerRepository.getAll().size());
        assertEquals(customer1, customerRepository.getAll().stream().filter(customer -> customer.getId() == customer1.getId()).findFirst().get());
        assertEquals(customer2, customerRepository.getAll().stream().filter(customer -> customer.getId() == customer2.getId()).findFirst().get());
        assertEquals(customer3, customerRepository.getAll().stream().filter(customer -> customer.getId() == customer3.getId()).findFirst().get());
    }

    @Test
    void getByTaxId() {
        assertEquals(customer1, customerRepository.getByTaxId(customer1.getTaxId()).get());
        assertEquals(customer2, customerRepository.getByTaxId(customer2.getTaxId()).get());
        assertEquals(customer3, customerRepository.getByTaxId(customer3.getTaxId()).get());
    }
}