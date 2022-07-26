package cards.repository;

import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
import common.repository.IRepository;

import java.util.Collection;
import java.util.Optional;

public interface ICardRepository extends IRepository<Card> {
    Collection<Card> findByAccountIdAndCustomerId(int accountId, int customerId);

    Optional<Card> findByCardNumber(String cardNumber);

    Collection<Card> findByCustomerId(int customerId);

    Optional<CreditCard> getCreditCardByCustomerId(int customerId);

    Optional<DebitCard> getDebitCardByCustomerId(int customerId);
}
