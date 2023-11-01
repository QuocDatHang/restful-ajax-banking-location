package com.cg.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @Column(name = "transaction_ammount", columnDefinition = "decimal(10,2)")
    @DecimalMin(value = "0.0", message = "Số tiền nạp phải lớn hơn 0")
    @DecimalMax(value = "1000000.0", message = "Số tiền nạp tối đa là 1.000.000")
    @NotNull(message = "Số tiền nạp không được để trống")
    private BigDecimal transactionAmount;
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;

    public Deposit() {
    }

    public Deposit(Long id, Customer customer, BigDecimal transactionAmount, Boolean deleted) {
        this.id = id;
        this.customer = customer;
        this.transactionAmount = transactionAmount;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

//    @Override
//    public boolean supports(Class<?> aClass) {
//        return false;
//    }

//    @Override
//    public void validate(Object o, Errors errors) {
//        Deposit deposit = (Deposit) o;
//        BigDecimal transactionAmount = deposit.transactionAmount;
//        if (transactionAmount == null) {
//            errors.rejectValue("transactionAmount", "deposit.transactionAmount.null",
//                    "Transaction Amount must not null");
//            return;
//        }
//        if (transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
//            errors.rejectValue("transactionAmount", "deposit.transactionAmount.min",
//                    "Transaction Amount must bigger 0");
//        }
//    }
}
