package cards.service;

import cards.exceptions.CardConflictException;
import cards.exceptions.CardNotFoundException;
import cards.model.CreditCard;
import cards.model.DebitCard;
import cards.repository.ICardRepository;
import cards.repository.InMemCardRepositoryImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardServiceTest {

    ICardRepository cardRepository = new InMemCardRepositoryImpl();
    ICardService cardService = new CardService(cardRepository);

    @Test
    void createDebitCard() throws CardConflictException {
        DebitCard card1 = cardService.createDebitCard(1, 1);

        DebitCard cardTest = new DebitCard(1,1,1,card1.getCardNumber(), card1.getPin(),false);

        assertEquals(card1, cardTest);
    }

    @Test
    void createCreditCard() throws CardConflictException {
        CreditCard card1 = cardService.createCreditCard(1, 1);

        CreditCard cardTest = new CreditCard(1,1,1, card1.getCardNumber(), card1.getPin(), 250, false);

        assertEquals(card1, cardTest);
    }

    @Test
    void deleteCard() throws CardConflictException, CardNotFoundException {
        CreditCard card1 = cardService.createCreditCard(1, 1);
        CreditCard card2 = cardService.createCreditCard(2, 2);
        CreditCard card3 = cardService.createCreditCard(3, 3);

        cardService.deleteCard(2);

        assertTrue(cardRepository.getAll().size() == 2);

    }

    @Test
    void checkCardNumberAndPassword() {
    }

    @Test
    void findAllCardsByCustomerId() {
    }

    @Test
    void findAllDebitCardsByCustomerId() {
    }

    @Test
    void getDebitCardByCardId() {
    }

    @Test
    void getDebitCardByCustomerId() {
    }

    @Test
    void getCreditCardByCardId() {
    }

    @Test
    void getCreditCardByCustomerId() {
    }

    @Test
    void getCardById() {
    }
}