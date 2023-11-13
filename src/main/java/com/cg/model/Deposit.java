package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deposits")
@Accessors(chain = true)
public class Deposit implements Validator{
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
        Deposit deposit = (Deposit) o;
        BigDecimal transactionAmount = deposit.transactionAmount;
        BigDecimal balance = deposit.getCustomer().getBalance();

        if (transactionAmount == null) {
            errors.rejectValue("transactionAmount", "deposit.transactionAmount.null",
                    "Transaction Amount must not null");
            return;
        }
        if (transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("transactionAmount", "deposit.transactionAmount.min",
                    "Transaction Amount must bigger 0");
            return;
        }
        if (transactionAmount.compareTo(BigDecimal.valueOf(1000000)) > 0 ) {
            errors.rejectValue("transactionAmount", "deposit.transactionAmount.max");
        }
    }
}
