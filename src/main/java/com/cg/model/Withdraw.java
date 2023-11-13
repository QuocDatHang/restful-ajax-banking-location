package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "withdraws")
@Accessors(chain = true)
public class Withdraw implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
    @Column(name = "transaction_ammount", columnDefinition = "decimal(10,0)")
    private BigDecimal transactionAmount;
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;


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

