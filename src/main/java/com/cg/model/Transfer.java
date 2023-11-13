package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfers")
public class Transfer implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    private Customer recipient;
    @Column(name = "transfer_amount", columnDefinition = "decimal(10,0)")
    private BigDecimal transferAmount;
    @Column(columnDefinition = "bigint default 10")
    private Long fees;
    @Column(name = "fees_amount", columnDefinition = "decimal(10,0)")
    private BigDecimal feesAmount;
    @Column(name = "transaction_amount", columnDefinition = "decimal(10,0)")
    private BigDecimal transactionAmount;
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;
    @Column(name = "create_at", columnDefinition = "DATETIME")
    private LocalDateTime createAt;


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Transfer transfer = (Transfer) o;
        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal senderBalance = transfer.getSender().getBalance();

        if (transferAmount == null) {
            errors.rejectValue("transferAmount", "transfer.transferAmount.null");
            return;
        }
        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("transferAmount", "transfer.transferAmount.min");
            return;
        }
        if (transferAmount.multiply(BigDecimal.valueOf(1.1)).compareTo(senderBalance) > 0) {
            errors.rejectValue("transferAmount", "transfer.transferAmount.biggerThanBalance");
        }
    }
}
