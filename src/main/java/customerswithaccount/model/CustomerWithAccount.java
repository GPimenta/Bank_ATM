package customerswithaccount.model;

import accounts.model.Account;
import cards.model.Card;
import cards.model.CreditCard;
import cards.model.DebitCard;
import common.model.IdentificationItem;
import costumers.model.Customer;
import utils.IPreconditions;

import java.util.Objects;

public class CustomerWithAccount implements IdentificationItem {

    private Integer id;
    private Customer customer;
    private Account account;
    private DebitCard debitCard;
    private CreditCard creditCard;

    public CustomerWithAccount(Customer customer, Account account){
        this.customer = IPreconditions.checkNotNull(customer,"Customer can not be null");
        this.account = IPreconditions.checkNotNull(account, "Account can not be null");
    }

    public CustomerWithAccount(Customer customer, Account account, DebitCard debitCard){
        this.customer = IPreconditions.checkNotNull(customer,"Customer can not be null");;
        this.account = IPreconditions.checkNotNull(account, "Account can not be null");
        this.debitCard = IPreconditions.checkNotNull(debitCard, "DebitCard can not be null");
    }

    public CustomerWithAccount(Customer customer, Account account, CreditCard creditCard){
        this.customer = IPreconditions.checkNotNull(customer,"Customer can not be null");;
        this.account = IPreconditions.checkNotNull(account, "Account can not be null");
        this.creditCard = IPreconditions.checkNotNull(creditCard, "CreditCard can not be null");
    }

    public CustomerWithAccount(Customer customer, Account account, DebitCard debitCard, CreditCard creditCard){
        this.customer = IPreconditions.checkNotNull(customer,"Customer can not be null");;
        this.account = IPreconditions.checkNotNull(account, "Account can not be null");
        this.debitCard = IPreconditions.checkNotNull(debitCard, "DebitCard can not be null");
        this.creditCard = IPreconditions.checkNotNull(creditCard, "CreditCard can not be null");
    }

//    public CustomerWithAccount(Customer customer, Account account, Card debitCard, Card creditCard) {
//        this.customer = IPreconditions.checkNotNull(customer,"Customer can not be null");;
//        this.account = IPreconditions.checkNotNull(account, "Account can not be null");
//
//        if (debitCard instanceof DebitCard) {
//            this.debitCard = (DebitCard) debitCard;
//        } else {
//            this.creditCard = (CreditCard) debitCard;
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerWithAccount that = (CustomerWithAccount) o;
        return Objects.equals(customer, that.customer) && Objects.equals(account, that.account) && Objects.equals(debitCard, that.debitCard) && Objects.equals(creditCard, that.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, account, debitCard, creditCard);
    }

    @Override
    public String toString() {
        return "CustomerWithAccount{" +
                "customer=" + customer +
                ", account=" + account +
                ", debitCard=" + debitCard +
                ", creditCard=" + creditCard +
                '}';
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }

    public void setDebitCard(DebitCard debitCard) {
        this.debitCard = debitCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class Builder{
        private Integer id;
        private Customer customer;
        private Account account;
        private DebitCard debitCard;
        private CreditCard creditCard;

        public Builder(){

        }

        public Builder withId(){
            this.id = id;
            return this;
        }

        public Builder withCostumer(Customer customer){
            this.customer = customer;
            return this;
        }

        public Builder withAccount(Account account){
            this.account = account;
            return this;
        }

        public Builder withDebidCard(DebitCard debitCard){
            this.debitCard = debitCard;
            return this;
        }

        public Builder withCreditCard(CreditCard creditCard){
            this.creditCard = creditCard;
            return this;
        }

        public CustomerWithAccount build(){
            CustomerWithAccount customerWithAccount = new CustomerWithAccount(customer, account);
            return customerWithAccount;
        }

        public CustomerWithAccount buildDebitCard(){
            CustomerWithAccount customerWithAccount = new CustomerWithAccount(customer, account, debitCard);
            return customerWithAccount;
        }

        public CustomerWithAccount buildCreditCard(){
            CustomerWithAccount customerWithAccount = new CustomerWithAccount(customer, account, creditCard);
            return customerWithAccount;
        }

        public CustomerWithAccount buildAllCard(){
            CustomerWithAccount customerWithAccount = new CustomerWithAccount(customer, account, debitCard, creditCard);
            return customerWithAccount;
        }
    }


}
