package costumers.repository;

import costumers.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.INumbersGenerator;

import java.time.LocalDate;

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
    }

    @Test
    void update() {
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getByTaxId() {
    }
}