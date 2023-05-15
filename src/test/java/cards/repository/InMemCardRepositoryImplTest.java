package cards.repository;

import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
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
    }

    @Test
    void getAllDebitCardByCustomerId() {
    }

    @Test
    void getCreditCardByCustomerId() {
    }

    @Test
    void getDebitCardByCustomerId() {
    }
}