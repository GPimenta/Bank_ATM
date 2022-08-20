package transaction.model;

import common.model.IdentificationItem;
import utils.IPreconditions;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction implements IdentificationItem {
    private Integer id;
    private Integer fromAccountId;
    private Integer toAccoundId;
    private Integer cardId;
    private LocalDateTime timestamp;
    private String amount;


    public Transaction(Integer id, Integer fromAccountId, Integer toAccoundId, Integer cardId, LocalDateTime timestamp, String amount) {
        this.id = id;
        this.fromAccountId = IPreconditions.checkNotNull(fromAccountId, "From Account Id cannot be null");
        this.toAccoundId = IPreconditions.checkNotNull(toAccoundId, "To Account Id cannot be null");
        this.cardId = IPreconditions.checkNotNull(cardId, "Card Id cannot be null");
        this.timestamp = IPreconditions.requireNonNullElse(timestamp, LocalDateTime.now());
        this.amount = IPreconditions.checkNotNull(amount, "Amount cannot be null");
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", fromAccountId=" + fromAccountId +
                ", toAccoundId=" + toAccoundId +
                ", cardId=" + cardId +
                ", timestamp=" + timestamp +
                ", amount='" + amount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(fromAccountId, that.fromAccountId) && Objects.equals(toAccoundId, that.toAccoundId) && Objects.equals(cardId, that.cardId) && Objects.equals(timestamp, that.timestamp) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromAccountId, toAccoundId, cardId, timestamp, amount);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Integer getToAccoundId() {
        return toAccoundId;
    }

    public void setToAccoundId(Integer toAccoundId) {
        this.toAccoundId = toAccoundId;
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
        private Integer fromAccountId;
        private Integer toAccountId;
        private Integer cardId;
        private LocalDateTime timestamp;
        private String amount;

        public Builder() {

        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withFromAccountId(Integer accountId) {
            this.fromAccountId = accountId;
            return this;
        }
        public Builder withToAccountId(Integer accountId) {
            this.toAccountId = accountId;
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
            return new Transaction(id, fromAccountId, toAccountId, cardId, timestamp, amount);
        }
    }

}
