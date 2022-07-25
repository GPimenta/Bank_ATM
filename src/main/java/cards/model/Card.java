package cards.model;

import common.model.IdentificationItem;
import utils.IPreconditions;

import java.util.Objects;


public class Card implements IdentificationItem {

    public static final int CARD_NUMBER_LENGTH = 5;
    public static final int PIN_NUMBER_LENGTH = 4;


    private Integer id;
    private final Integer customerId;
    private final Integer accountId;
    private final String cardNumber;
    private String pin;
    private Boolean used;
    private final Boolean isCreditCard;

    public Card(Integer id, Integer customerId, Integer accountId, String cardNumber, String pin, Boolean used, Boolean isCreditCard) {
        this.id = id;
        this.customerId = customerId;
        this.accountId = accountId;
        this.cardNumber = IPreconditions.checkLength(cardNumber, CARD_NUMBER_LENGTH, "Card number must have 5 digits");
        this.pin = IPreconditions.checkLength(pin, PIN_NUMBER_LENGTH, "Card Pin must have 4 digits");
        this.used = used;
        this.isCreditCard = isCreditCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(customerId, card.customerId) && Objects.equals(accountId, card.accountId) && Objects.equals(cardNumber, card.cardNumber) && Objects.equals(pin, card.pin) && Objects.equals(used, card.used) && Objects.equals(isCreditCard, card.isCreditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, accountId, cardNumber, pin, used, isCreditCard);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Boolean getCrediCard() {
        return isCreditCard;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", accountId=" + accountId +
                ", cardNumber='" + cardNumber + '\'' +
                ", pin='" + pin + '\'' +
                ", used=" + used +
                ", isCrediCard=" + isCreditCard +
                '}';
    }
}
