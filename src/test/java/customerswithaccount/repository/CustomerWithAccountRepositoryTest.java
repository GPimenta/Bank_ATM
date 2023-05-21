package customerswithaccount.repository;

import accounts.model.Account;
import cards.model.CreditCard;
import cards.model.DebitCard;
import costumers.model.Customer;
import customerswithaccount.model.CustomerWithAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.INumbersGenerator;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerWithAccountRepositoryTest {

    private static String generateTaxId(){
        return INumbersGenerator.createString(9);
    }

    CustomerWithAccountRepository repository = new CustomerWithAccountRepository();


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

    Customer customer4 = new Customer.Builder()
            .withName("Scott")
            .withEmail("scott@email.com")
            .withTaxId(generateTaxId())
            .withBirthday(LocalDate.of(1900, 3, 8))
            .build();

    Account account1 = new Account.Builder()
            .withCustomerId(1)
            .withAccountNumber("12345")
            .withBalance(0D)
            .withPasswordAccount("1234")
            .withSecondaryOwnersId(List.of(2, 3))
            .build();

    Account account2 = new Account.Builder()
            .withCustomerId(2)
            .withAccountNumber("54321")
            .withBalance(0D)
            .withPasswordAccount("4321")
            .withSecondaryOwnersId(List.of(1, 3))
            .build();

    Account account3 = new Account.Builder()
            .withCustomerId(3)
            .withAccountNumber("51423")
            .withBalance(0D)
            .withPasswordAccount("4132")
            .withSecondaryOwnersId(List.of(1, 2))
            .build();

    Account account4 = new Account.Builder()
            .withCustomerId(1)
            .withAccountNumber("91823")
            .withBalance(0D)
            .withPasswordAccount("9182")
            .withSecondaryOwnersId(List.of(3, 1))
            .build();

    Account account5 = new Account.Builder()
            .withCustomerId(4)
            .withAccountNumber("91823")
            .withBalance(0D)
            .withPasswordAccount("9182")
            .withSecondaryOwnersId(List.of(2, 3))
            .build();

    DebitCard debitCard1 = new DebitCard.Builder()
            .isUsed(false)
            .withAccountId(1)
            .withCustomerId(1)
            .withCardNumber("12345")
            .withPin("1234")
            .build();

    DebitCard debitCard2 = new DebitCard.Builder()
            .isUsed(true)
            .withAccountId(2)
            .withCustomerId(2)
            .withCardNumber("54321")
            .withPin("4321")
            .build();

    DebitCard debitCard3 = new DebitCard.Builder()
            .isUsed(false)
            .withAccountId(1)
            .withCustomerId(2)
            .withCardNumber("15234")
            .withPin("4132")
            .build();
    DebitCard debitCard4 = new DebitCard.Builder()
            .isUsed(false)
            .withAccountId(4)
            .withCustomerId(4)
            .withCardNumber("11111")
            .withPin("1111")
            .build();
    CreditCard creditCard1 = new CreditCard.Builder()
            .isUsed(false)
            .withAccountId(1)
            .withCustomerId(1)
            .withCardNumber("67890")
            .withPin("6789")
            .withCashAdvance(250)
            .build();

    CreditCard creditCard2 = new CreditCard.Builder()
            .isUsed(true)
            .withAccountId(2)
            .withCustomerId(2)
            .withCardNumber("09876")
            .withPin("9876")
            .withCashAdvance(250)
            .build();
    CreditCard creditCard3 = new CreditCard.Builder()
            .isUsed(false)
            .withAccountId(1)
            .withCustomerId(2)
            .withCardNumber("06978")
            .withPin("9687")
            .withCashAdvance(250)
            .build();

    CreditCard creditCard4 = new CreditCard.Builder()
            .isUsed(false)
            .withAccountId(4)
            .withCustomerId(4)
            .withCardNumber("22222")
            .withPin("2222")
            .withCashAdvance(250)
            .build();

    CustomerWithAccount customerWithAccount1 = new CustomerWithAccount.Builder()
            .withCostumer(customer1)
            .withAccount(account1)
            .build();

    CustomerWithAccount customerWithAccount2 = new CustomerWithAccount.Builder()
            .withCostumer(customer2)
            .withAccount(account2)
            .withDebidCard(debitCard2)
            .buildDebitCard();

    CustomerWithAccount customerWithAccount3 = new CustomerWithAccount.Builder()
            .withCostumer(customer3)
            .withAccount(account3)
            .withCreditCard(creditCard3)
            .buildCreditCard();

    CustomerWithAccount customerWithAccount4 = new CustomerWithAccount.Builder()
            .withCostumer(customer4)
            .withAccount(account4)
            .withDebidCard(debitCard4)
            .withCreditCard(creditCard4)
            .buildAllCard();

    CustomerWithAccount customerWithAccount5 = new CustomerWithAccount.Builder()
            .withCostumer(customer1)
            .withAccount(account4)
            .withDebidCard(debitCard4)
            .withCreditCard(creditCard4)
            .buildAllCard();

    @BeforeEach
    void setup(){
        repository.create(customerWithAccount1);
        repository.create(customerWithAccount2);
        repository.create(customerWithAccount3);
        repository.create(customerWithAccount4);
        repository.create(customerWithAccount5);
    }

    @AfterEach
    void end() {
        repository.getAll().clear();
    }

    @Test
    void create() {
        assertEquals(customerWithAccount1, repository.getAll().stream().filter(customerWithAccount -> customerWithAccount.equals(customerWithAccount1)).findFirst().get());
        assertEquals(customerWithAccount2, repository.getAll().stream().filter(customerWithAccount -> customerWithAccount.equals(customerWithAccount2)).findFirst().get());
        assertEquals(customerWithAccount3, repository.getAll().stream().filter(customerWithAccount -> customerWithAccount.equals(customerWithAccount3)).findFirst().get());
        assertEquals(customerWithAccount4, repository.getAll().stream().filter(customerWithAccount -> customerWithAccount.equals(customerWithAccount4)).findFirst().get());

    }

    @Test
    void deleteById() {
        assertTrue(repository.deleteById(customerWithAccount2.getId()));
        assertEquals(4, repository.getAll().size());
        assertEquals(customerWithAccount1, repository.getAll().stream().filter(customerWithAccount -> customerWithAccount.equals(customerWithAccount1)).findFirst().get());
        assertEquals(customerWithAccount3, repository.getAll().stream().filter(customerWithAccount -> customerWithAccount.equals(customerWithAccount3)).findFirst().get());
        assertEquals(customerWithAccount4, repository.getAll().stream().filter(customerWithAccount -> customerWithAccount.equals(customerWithAccount4)).findFirst().get());
    }

    @Test
    void update() {
        final CustomerWithAccount customerWithAccountTest = customerWithAccount1;
        customerWithAccountTest.setDebitCard(debitCard3);
        repository.update(customerWithAccountTest);
        assertEquals(debitCard3, repository.getById(customerWithAccount1.getId()).get().getDebitCard());
    }

    @Test
    void getById() {
        assertEquals(customerWithAccount1, repository.getById(customerWithAccount1.getId()).get());
    }

    @Test
    void getAll() {
        assertTrue(repository.getAll().containsAll(List.of(customerWithAccount1, customerWithAccount2, customerWithAccount3, customerWithAccount4, customerWithAccount5)));
    }

    @Test
    void findCustomerWithAccountThroughAccountNumber() {
        assertEquals(customerWithAccount2, repository.findCustomerWithAccountThroughAccountNumber(customerWithAccount2.getAccount().getAccountNumber()).get());
    }

    @Test
    void findAllCustomerWithAccountThroughTaxId() {
        assertTrue(repository.findAllCustomerWithAccountThroughTaxId(customerWithAccount1.getCustomer().getTaxId()).containsAll(List.of(customerWithAccount1, customerWithAccount5)));
    }

    @Test
    void findCustomerWithAccountThroughDebitCard() {
        assertEquals(customerWithAccount2, repository.findCustomerWithAccountThroughDebitCard(customerWithAccount2.getDebitCard().getCardNumber()).get());
    }

    @Test
    void findCustomerWithAccountThroughCreditCard() {
        assertEquals(customerWithAccount3, repository.findCustomerWithAccountThroughCreditCard(customerWithAccount3.getCreditCard().getCardNumber()).get());

    }

    @Test
    void findCustomerWithAccountThroughCard() {
        assertEquals(customerWithAccount4, repository.findCustomerWithAccountThroughCard(customerWithAccount4.getDebitCard().getCardNumber()).get());
        assertEquals(customerWithAccount4, repository.findCustomerWithAccountThroughCard(customerWithAccount4.getCreditCard().getCardNumber()).get());
    }
}