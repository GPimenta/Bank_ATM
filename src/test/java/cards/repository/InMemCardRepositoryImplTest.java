package cards.repository;

import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class InMemCardRepositoryImplTest {

    ICardRepository cardRepository = new InMemCardRepositoryImpl();

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

    @BeforeEach
    void setup() {
        cardRepository.create(debitCard1);
        cardRepository.create(debitCard2);
        cardRepository.create(debitCard3);
        cardRepository.create(creditCard1);
        cardRepository.create(creditCard2);
        cardRepository.create(creditCard3);
    }

    @AfterEach
    void end() {
        cardRepository.getAll().clear();
    }

    @Test
    void create() {
        assertEquals(debitCard1,cardRepository.getAll().stream().findFirst().get());
    }

    @Test
    void findByAccountIdAndCustomerId() {
        final Collection<Card> cards = cardRepository.findByAccountIdAndCustomerId(1, 1);
        assertTrue(cards.contains(debitCard1));
        assertTrue(cards.contains(creditCard1));
        assertFalse(cards.contains(creditCard2));
        assertFalse(cards.contains(debitCard2));
        assertFalse(cards.contains(creditCard3));
        assertFalse(cards.contains(debitCard3));
    }

    @Test
    void findByCardNumber() {
        assertEquals(debitCard1, cardRepository.findByCardNumber(debitCard1.getCardNumber()).get());
        assertNotEquals(creditCard1, cardRepository.findByCardNumber(debitCard1.getCardNumber()).get());
        assertNotEquals(debitCard3, cardRepository.findByCardNumber(debitCard1.getCardNumber()).get());
    }

    @Test
    void findByCustomerId() {
        assertTrue(cardRepository.findByCustomerId(1).contains(debitCard1));
        assertTrue(cardRepository.findByCustomerId(1).contains(creditCard1));
        assertFalse(cardRepository.findByCustomerId(1).contains(debitCard2));
        assertFalse(cardRepository.findByCustomerId(1).contains(debitCard3));
        assertFalse(cardRepository.findByCustomerId(1).contains(creditCard2));
        assertFalse(cardRepository.findByCustomerId(1).contains(creditCard3));
    }

    @Test
    void getAllCreditCardByCustomerId() {
        final Collection<CreditCard> allCreditCardByCustomerId = cardRepository.getAllCreditCardByCustomerId(2);
        assertEquals(2, allCreditCardByCustomerId.size());
        assertTrue(allCreditCardByCustomerId.contains(creditCard2));
        assertTrue(allCreditCardByCustomerId.contains(creditCard3));
        assertFalse(allCreditCardByCustomerId.contains(creditCard1));
    }

    @Test
    void getAllDebitCardByCustomerId() {
        final Collection<DebitCard> allDebitCardByCustomerId = cardRepository.getAllDebitCardByCustomerId(2);
        assertEquals(2, allDebitCardByCustomerId.size());
        assertTrue(allDebitCardByCustomerId.contains(debitCard2));
        assertTrue(allDebitCardByCustomerId.contains(debitCard3));
        assertFalse(allDebitCardByCustomerId.contains(debitCard1));
    }

    @Test
    void getCreditCardByCustomerIdAndAccountId() {
        assertEquals(creditCard1, cardRepository.getCreditCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(creditCard2, cardRepository.getCreditCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(creditCard3, cardRepository.getCreditCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(debitCard1, cardRepository.getCreditCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(debitCard2, cardRepository.getCreditCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(debitCard3, cardRepository.getCreditCardByCustomerIdAndAccountId(1,1).get());
    }

    @Test
    void getDebitCardByCustomerIdAndAccountId() {
        assertEquals(debitCard1, cardRepository.getDebitCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(debitCard2, cardRepository.getDebitCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(debitCard3, cardRepository.getDebitCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(creditCard1, cardRepository.getDebitCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(creditCard2, cardRepository.getDebitCardByCustomerIdAndAccountId(1,1).get());
        assertNotEquals(creditCard3, cardRepository.getDebitCardByCustomerIdAndAccountId(1,1).get());
    }
}