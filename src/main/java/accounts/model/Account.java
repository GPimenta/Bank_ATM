package accounts.model;

import common.model.IdentificationItem;
import utils.IPreconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Account implements IdentificationItem {

    public static final int ACCOUNT_NUMBER_LENGTH = 5;
    public static final int MAX_NUMBER_SECONDARY_OWNERS = 4;
    public static final int ACCOUNT_PASSWORD_LENGTH = 4;


    private Integer id;
    private final Integer customerId;
    private String password;
    private final String accountNumber; // 5 digits
    private Double balance;
    private List<Integer> secondaryOwnersId;

    public Account(Integer id, Integer customerId, String password, String accountNumber, Double balance,
                   List<Integer> secondaryOwnersId) {
        this.id = id;
        this.customerId = customerId;
        this.password = IPreconditions.checkLength(password, ACCOUNT_PASSWORD_LENGTH,
                "The account password must have 4 digits");
        this.accountNumber = IPreconditions.checkLength(accountNumber, ACCOUNT_NUMBER_LENGTH,
                "The account number can only be 5 digits");
        this.balance = balance;
//		this.secondaryOwnersId = IPreconditions.requireNonNullElse(secondaryOwnersId, Collections.emptyList());
        this.secondaryOwnersId = IPreconditions.requireNonNullElse(secondaryOwnersId, new ArrayList<>(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && customerId == account.customerId && Double.compare(account.balance, balance) == 0 && Objects.equals(password, account.password) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(secondaryOwnersId, account.secondaryOwnersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, password, accountNumber, balance, secondaryOwnersId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", password='" + password + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", secondaryOwnersId=" + secondaryOwnersId +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Integer> getSecondaryOwnersId() {
        return secondaryOwnersId;
    }

    public void setSecondaryOwnersId(List<Integer> secondaryOwnersId) {
        this.secondaryOwnersId = secondaryOwnersId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class Builder{
        private Integer id;
        private Integer customerId;
        private String password;
        private String accountNumber;
        private Double balance;
        private List<Integer> secondaryOwnersId;

        public Builder(){

        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withCustomerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withPasswordAccount(String password) {
            this.password = password;
            return this;
        }

        public Builder withAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder withBalance(Double balance) {
            this.balance = balance;
            return this;
        }

        public Builder withSecondaryOwnersId(Collection<Integer> secondaryOwnersId) {
            this.secondaryOwnersId = new ArrayList<>(secondaryOwnersId);
            return this;
        }

        public Account build() {
            return new Account(id, customerId, password, accountNumber, balance, secondaryOwnersId);
        }

    }


}
