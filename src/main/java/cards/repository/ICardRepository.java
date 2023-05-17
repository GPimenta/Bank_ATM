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

    Collection<CreditCard> getAllCreditCardByCustomerId(int customerId);

    Collection<DebitCard> getAllDebitCardByCustomerId(int customerId);

    Optional<CreditCard> getCreditCardByCustomerIdAndAccountId(int customerId, int accountId);

    Optional<DebitCard> getDebitCardByCustomerIdAndAccountId(int customerId, int accountId);



}
