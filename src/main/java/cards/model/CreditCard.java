package cards.model;

import utils.IPreconditions;

import java.util.Objects;

public class CreditCard extends Card{
    public static final int ALLOWED_LIMIT_CASH_ADVANCE = 250;

    private Integer cashAdvance;


    public CreditCard(Integer id, Integer customerId, Integer accountId, String cardNumber, String pin,
                      Integer cashAdvance, Boolean used) {
        super(id, customerId, accountId, cardNumber, pin, used, true);

        IPreconditions.checkArgument(cashAdvance <= ALLOWED_LIMIT_CASH_ADVANCE, "The limit amount is: " + ALLOWED_LIMIT_CASH_ADVANCE);

        this.cashAdvance = IPreconditions.requireNonNullElse(cashAdvance, ALLOWED_LIMIT_CASH_ADVANCE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(cashAdvance, that.cashAdvance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cashAdvance);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cashAdvance=" + cashAdvance +
                '}';
    }

    public Integer getCashAdvance() {
        return cashAdvance;
    }

    public void setCashAdvance(Integer cashAdvance) {
        this.cashAdvance = cashAdvance;
    }


    public static class Builder {
        private Integer id;
        private Integer customerId;
        private Integer accountId;
        private String cardNumber;
        private String pin;
        private Integer cashAdvance;
        private Boolean used;

        public Builder() {

        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withCustomerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withAccountId(Integer accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder withCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder withPin(String pin) {
            this.pin = pin;
            return this;
        }

        public Builder withCashAdvance(Integer cashAdvance) {
            this.cashAdvance = cashAdvance;
            return this;
        }

        public Builder isUsed(Boolean used) {
            this.used = used;
            return this;
        }

        public CreditCard build() {
            return new CreditCard(id, customerId, accountId, cardNumber, pin, cashAdvance, used);
        }

    }

}
