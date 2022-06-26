package transaction.model;

import common.model.IdentificationItem;
import utils.IPreconditions;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction implements IdentificationItem {
    private Integer id;
    private Integer accountID;
    private Integer cardId;
    private LocalDateTime timestamp;
    private String amount;


    public Transaction(Integer id, Integer accountID, Integer cardId, LocalDateTime timestamp, String amount) {
        this.id = id;
        this.accountID = IPreconditions.checkNotNull(accountID, "Account Id cannot be null");
        this.cardId = IPreconditions.checkNotNull(cardId, "Card Id cannot be null");
        this.timestamp = IPreconditions.requireNonNullElse(timestamp, LocalDateTime.now());
        this.amount = IPreconditions.checkNotNull(amount, "Amount cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(accountID, that.accountID) && Objects.equals(cardId, that.cardId) && Objects.equals(timestamp, that.timestamp) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountID, cardId, timestamp, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountId=" + accountID +
                ", cardId=" + cardId +
                ", timestamp=" + timestamp +
                ", amount='" + amount + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public static class Builder {
        private Integer id;
        private Integer accountId;
        private Integer cardId;
        private LocalDateTime timestamp;
        private String amount;

        public Builder() {

        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withAccountId(Integer accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder withCardId(Integer cardId) {
            this.cardId = cardId;
            return this;
        }
        public Builder withTimeStamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        public Builder withAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public Transaction build() {
            return new Transaction(id, accountId,cardId, timestamp,amount);
        }
    }

}
