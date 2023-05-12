package cards.service;

import cards.exceptions.CardConflictException;
import cards.exceptions.CardNotFoundException;
import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;

import java.util.Collection;

public interface ICardService {

    DebitCard createDebitCard(Integer accountId, Integer customerId) throws CardConflictException;

    CreditCard createCreditCard(Integer accountId, Integer customerId) throws CardConflictException;

    void deleteCard(Integer cardId) throws CardNotFoundException;

    boolean checkIfDebitCard(String cardNumber);

    boolean checkCardNumberAndPassword(String cardNumber, String pin);

    Card updateCard(String cardNumber) throws CardConflictException, CardNotFoundException;

    Collection<Card> findAllCardsByCustomerId(Integer customerId);

    Collection<DebitCard> findAllDebitCardsByCustomerId(Integer customerId);
    Collection<CreditCard> findAllCreditCardsByCustomerId(Integer customerId);

    DebitCard getDebitCardByCardNumber(String cardNumber);

    DebitCard getDebitCardByCustomerId(Integer customerId);

    CreditCard getCreditCardByCardNumber(String cardNumber);

    CreditCard getCreditCardByCustomerId(Integer customerId);

    Card getCardByCardNumber(String cardId);


}
