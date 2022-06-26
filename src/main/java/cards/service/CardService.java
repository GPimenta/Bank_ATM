package cards.service;

import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
import cards.repository.ICardRepository;
import utils.IPreconditions;

import java.util.Collection;

public class CardService implements ICardService {

    private final ICardRepository repository;

    public CardService(ICardRepository repository) {
        this.repository = IPreconditions.checkNotNull(repository,"Card repository cannot be null");
    }

    @Override
    public DebitCard createDebitCard(Integer accountId, Integer customerId) {
        return null;
    }

    @Override
    public CreditCard createCreditCard(Integer accountId, Integer customerId) {
        return null;
    }

    @Override
    public void deleteCard(Integer cardId) {

    }

    @Override
    public boolean checkCardNumberAndPassword(String cardNumber, String password) {
        return false;
    }

    @Override
    public Collection<Card> findAllCardsByCustomerId(Integer customerId) {
        return null;
    }

    @Override
    public Collection<DebitCard> findAllDebitCardsByCustomerId(Integer customerId) {
        return null;
    }

    @Override
    public DebitCard getDebitCardByCardId(String cardId) {
        return null;
    }

    @Override
    public CreditCard getCreditCardByCardId(String cardId) {
        return null;
    }

    @Override
    public Card getCardById(Integer cardId) {
        return null;
    }
}
