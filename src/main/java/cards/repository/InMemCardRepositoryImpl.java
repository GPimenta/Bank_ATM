package cards.repository;

import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
import common.repository.InMemRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemCardRepositoryImpl extends InMemRepository<Card> implements ICardRepository {

    private static Integer cardIdCount = 0;

    private static int generateCardId(){
        return ++cardIdCount;
    }

    @Override
    public Optional<Card> create(Card newItem){
        final Card item;
        if (newItem instanceof DebitCard){
            DebitCard debitCard = (DebitCard) newItem;
            debitCard.setId(generateCardId());
            item = debitCard;
        } else{
            CreditCard creditCard = (CreditCard) newItem;
            creditCard.setId(generateCardId());
            item = creditCard;
        }
        if (getAll().stream().anyMatch(card -> card.getId().equals(newItem.getId()) || card.getCardNumber().equals(newItem.getCardNumber()))){
            return Optional.empty();
        }
        return super.create(item);
    }

    @Override
    public Collection<Card> findByAccountIdAndCustomerId(int accountId, int customerId){
        return getAll().stream()
                .filter(card -> card.getAccountId() == accountId)
                .filter(card -> card.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Card> findByCardNumber(String cardNumber){
        return getAll().stream()
                .filter(card -> card.getCardNumber().equals(cardNumber)).findFirst();
    }

    @Override
    public Collection<Card> findByCustomerId(int customerId){
        return getAll().stream()
                .filter(card -> card.getCustomerId().equals(customerId)).collect(Collectors.toList());
    }

    @Override
    public Optional<CreditCard> getCreditCardByCustomerId(int customerId){
        return getAll().stream()
                .filter(card -> card.getId() == customerId)
                .filter(card -> card instanceof CreditCard)
                .map(card -> (CreditCard) card)
                .findFirst();
    }

    @Override
    public Optional<DebitCard> getDebitCardByCustomerId(int customerId){
        return getAll().stream()
                .filter(card -> card.getId() == customerId)
                .filter(card -> card instanceof DebitCard)
                .map(card -> (DebitCard) card)
                .findFirst();
    }


}
