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

    boolean checkCardNumberAndPassword(String cardNumber, String pin);

    Collection<Card> findAllCardsByCustomerId(Integer customerId);

    Collection<DebitCard> findAllDebitCardsByCustomerId(Integer customerId);

    DebitCard getDebitCardByCardId(String cardId);

    CreditCard getCreditCardByCardId(String cardId);

    Card getCardById(Integer cardId);


}
