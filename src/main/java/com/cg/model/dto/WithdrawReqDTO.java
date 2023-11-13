package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WithdrawReqDTO implements Validator {
    private BigDecimal transactionAmount;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        WithdrawReqDTO WithdrawReqDTO = (WithdrawReqDTO) o;
        BigDecimal transactionAmountWithdraw = WithdrawReqDTO.transactionAmount;

        if (transactionAmountWithdraw == null) {
            errors.rejectValue("transactionAmountWithdraw", "transactionAmountWithdraw.null",
                    "Vui lòng nhập số tiền cần nạp");
        }
        if (transactionAmountWithdraw.compareTo(BigDecimal.ZERO) <= 0 ) {
            errors.rejectValue("transactionAmountWithdraw", "transactionAmountWithdraw.zero",
                    "Số tiền nạp phải lớn hơn 0");
        }
    }

    public Withdraw toWithdraw() {
        return new Withdraw()
                .setTransactionAmount(transactionAmount);
    }
}
