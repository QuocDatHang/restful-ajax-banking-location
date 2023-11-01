package com.cg.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
public class Withdraw implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @Column(name = "transaction_ammount", columnDefinition = "decimal(10,2)")
    private BigDecimal transactionAmount;
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;

    public Withdraw() {
    }

    public Withdraw(Long id, Customer customer, BigDecimal transactionAmount, Boolean deleted) {
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

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Withdraw withdraw = (Withdraw) o;
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        BigDecimal balance = withdraw.getCustomer().getBalance();
        if (transactionAmount == null) {
            errors.rejectValue("transactionAmount", "withdraw.transactionAmount.null");
            return;
        }
        if (transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("transactionAmount", "withdraw.transactionAmount.min");
            return;
        }
        if (transactionAmount.compareTo(balance) >= 0) {
            errors.rejectValue("transactionAmount", "withdraw.transactionAmount.max");
        }
    }
}

