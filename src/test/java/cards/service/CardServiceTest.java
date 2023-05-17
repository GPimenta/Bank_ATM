package cards.service;

import cards.exceptions.CardConflictException;
import cards.exceptions.CardNotFoundException;
import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
import cards.repository.ICardRepository;
import cards.repository.InMemCardRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

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
    void checkCardNumberAndPassword() throws CardConflictException {
        CreditCard card1 = cardService.createCreditCard(1, 1);
        String cardNumber = card1.getCardNumber();
        String pin = card1.getPin();


        assertTrue(cardService.checkCardNumberAndPassword(cardNumber,pin));

    }

    @Test
    void findAllCardsByCustomerId() throws CardConflictException {
        CreditCard creditCard1 = cardService.createCreditCard(1, 1);
        DebitCard debitCard1 = cardService.createDebitCard(1, 1);
        DebitCard debitCard2 = cardService.createDebitCard(2, 1);
        DebitCard debitCard3 = cardService.createDebitCard(3, 2);

        Collection<Card> cards = cardService.findAllCardsByCustomerId(1);

        Collection<Card> cardsTest = List.of(creditCard1, debitCard1, debitCard2);

        assertEquals(cards, cardsTest);

    }

    @Test
    void findAllDebitCardsByCustomerId() throws CardConflictException {
        CreditCard creditCard1 = cardService.createCreditCard(1, 1);
        DebitCard debitCard1 = cardService.createDebitCard(1, 1);
        DebitCard debitCard2 = cardService.createDebitCard(2, 1);
        DebitCard debitCard3 = cardService.createDebitCard(3, 2);

        Collection<DebitCard> cardsActual = List.of(debitCard1, debitCard2);

        Collection<DebitCard> cards = cardService.findAllDebitCardsByCustomerId(1);

        assertEquals(cards, cardsActual);

    }

    @Test
    void findAllCreditCardsByCustomerId() throws CardConflictException {
        CreditCard creditCard1 = cardService.createCreditCard(1, 1);
        CreditCard creditCard2 = cardService.createCreditCard(2, 1);
        DebitCard debitCard2 = cardService.createDebitCard(2, 1);
        DebitCard debitCard3 = cardService.createDebitCard(3, 2);

        Collection<CreditCard> cardsActual = List.of(creditCard1, creditCard2);

        Collection<CreditCard> cards = cardService.findAllCreditCardsByCustomerId(1);

        assertEquals(cards, cardsActual);
    }

    @Test
    void getDebitCardByCardNumber() throws CardConflictException {
        CreditCard creditCard1 = cardService.createCreditCard(1, 1);
        DebitCard debitCard1 = cardService.createDebitCard(1, 1);
        DebitCard debitCard2 = cardService.createDebitCard(2, 1);
        DebitCard debitCard3 = cardService.createDebitCard(3, 2);

        DebitCard getDebitCard = cardService.getDebitCardByCardNumber(debitCard1.getCardNumber());

        assertEquals(debitCard1.getCardNumber(), getDebitCard.getCardNumber());

    }

    @Test
    void getDebitCardByCustomerId() throws CardConflictException {
        DebitCard debitCard1 = cardService.createDebitCard(1, 1);
        DebitCard debitCard = cardService.getDebitCardByCustomerIdAndAccountId(1,1 );

        assertEquals(debitCard, debitCard1);
    }

    @Test
    void getCreditCardByCardNumber() throws CardConflictException {
        CreditCard creditCard1 = cardService.createCreditCard(1, 1);
        CreditCard creditCard = cardService.getCreditCardByCardNumber(creditCard1.getCardNumber());

        assertEquals(creditCard.getCardNumber(), creditCard1.getCardNumber());

    }

    @Test
    void getCreditCardByCustomerId() throws CardConflictException {
        CreditCard creditCard1 = cardService.createCreditCard(1, 1);
        CreditCard creditCard = cardService.getCreditCardByCustomerIdAndAccountId(1, 1);

        assertEquals(creditCard, creditCard1);
    }

    @Test
    void getCardByCardNumber() throws CardConflictException {
        Card card = (Card)cardService.createCreditCard(1, 1);

        Card card1 = cardService.getCardByCardNumber(card.getCardNumber());

        assertEquals(card, card1);

    }
}