package cards.model;

public class DebitCard extends Card{

    public DebitCard(Integer id, Integer customerId, Integer accountId, String cardNumber, String pin, Boolean used) {
        super(id, customerId, accountId, cardNumber, pin, used, false);
    }

    public static class Builder{
        private Integer id;
        private Integer customerId;
        private Integer accountId;
        private String cardNumber;
        private String pin;
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
        public Builder isUsed(Boolean used) {
            this.used = used;
            return this;
        }


        public DebitCard build() {
            return new DebitCard(id, customerId, accountId, cardNumber, pin, used);
        }

    }
}
