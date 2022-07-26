package cards.service;

import cards.exceptions.CardConflictException;
import cards.exceptions.CardNotFoundException;
import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
import cards.repository.ICardRepository;
import utils.INumbersGenerator;
import utils.IPreconditions;

import java.util.Collection;

public class CardService implements ICardService {

    private final ICardRepository repository;

    public CardService(ICardRepository repository) {
        this.repository = IPreconditions.checkNotNull(repository,"Card repository cannot be null");
    }

    private static String generateCardNumber(){
        return INumbersGenerator.createString(Card.CARD_NUMBER_LENGTH);
    }

    private static String generatePinNumber(){
        return  INumbersGenerator.createString(Card.PIN_NUMBER_LENGTH);
    }

    @Override
    public DebitCard createDebitCard(Integer accountId, Integer customerId) throws CardConflictException {
        DebitCard debitCard = new DebitCard.Builder()
                .isUsed(false)
                .withAccountId(accountId)
                .withCustomerId(customerId)
                .withCardNumber(generateCardNumber())
                .withPin(generatePinNumber())
                .build();
        return (DebitCard) repository.create(debitCard).orElseThrow(() -> new CardConflictException("Conflict" +
                " on creating DebitCard with customer Id: '%i'", customerId));
    }

    @Override
    public CreditCard createCreditCard(Integer accountId, Integer customerId) throws CardConflictException {
        CreditCard creditCard = new CreditCard.Builder()
                .isUsed(false)
                .withAccountId(accountId)
                .withCustomerId(customerId)
                .withCardNumber(generateCardNumber())
                .withPin(generatePinNumber())
                .build();
        return (CreditCard) repository.create(creditCard).orElseThrow(() -> new CardConflictException("Conflict" +
                " on creating DebitCard with customer Id: '%i'", customerId));
    }

    @Override
    public void deleteCard(Integer cardId) throws CardNotFoundException {
        if(!repository.deleteById(cardId)) {
            throw new CardNotFoundException("Card with Id %d not found to delete", cardId);
        }

    }

    @Override
    public boolean checkCardNumberAndPassword(String cardNumber, String pin) {
        if (repository.findByCardNumber(cardNumber).isPresent() &&
                repository.findByCardNumber(cardNumber).get().getPin().equals(pin)) {
            return true;
        }
        return false;
    }

    @Override
    public Collection<Card> findAllCardsByCustomerId(Integer customerId) {
        return repository.findByCustomerId(customerId);
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
