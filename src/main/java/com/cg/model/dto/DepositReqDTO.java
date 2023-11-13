package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

public class DepositReqDTO implements Validator {
    private BigDecimal transactionAmount;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        DepositReqDTO depositReqDTO = (DepositReqDTO) o;
        BigDecimal transactionAmountDeposit = depositReqDTO.transactionAmount;

        if (transactionAmountDeposit == null) {
            errors.rejectValue("transactionAmountDeposit", "transactionAmountDeposit.null",
                    "Vui lòng nhập số tiền cần nạp");
        }
        if (transactionAmountDeposit.compareTo(BigDecimal.ZERO) <= 0 ) {
            errors.rejectValue("transactionAmountDeposit", "transactionAmountDeposit.zero",
                    "Số tiền nạp phải lớn hơn 0");
        }
    }
}
