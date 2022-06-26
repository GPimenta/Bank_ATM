package costumers.model;

import common.model.IdentificationItem;
import utils.IPreconditions;

import java.time.LocalDate;
import java.util.Objects;

public class Customer implements IdentificationItem, Comparable<Customer> {

    public static final int CUSTOMER_NAME_MIN_LENGTH = 3;
    public static final int CUSTOMER_TAXID_LENGTH = 9;

    private Integer id;
    private String name;
    private final String taxId;
    private String email;
    private final LocalDate birthday;

    public Customer(Integer id, String name, String taxId, String email, LocalDate birthday) {		this.id = id;
        this.name = IPreconditions.checkLengthIsGreaterThan(name, CUSTOMER_NAME_MIN_LENGTH, "Customer name must be greater than 3 characters");
        this.taxId = IPreconditions.checkLength(taxId, CUSTOMER_TAXID_LENGTH, "Customer taxId must be 9 digits");
        this.email = IPreconditions.checkNotNull(email, "Customer email can not be null");
        this.birthday = IPreconditions.checkNotNull(birthday, "Customer birthday can not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(taxId, customer.taxId) && Objects.equals(email, customer.email) && Objects.equals(birthday, customer.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, taxId, email, birthday);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", taxId='" + taxId + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxId() {
        return taxId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public  int compareTo(Customer customer){
        return this.getTaxId().compareTo(customer.getTaxId());
    }



    public static class Builder{
        private Integer id;
        private String name;
        private String taxId;
        private String email;
        private LocalDate birthday;


        public Builder() {

        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }
        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        public Builder withTaxId(String taxId) {
            this.taxId = taxId;
            return this;
        }
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder withBirthday(LocalDate birthday) {
            this.birthday= birthday;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer(id,name,taxId,email,birthday);
            return customer;
        }
    }
}
