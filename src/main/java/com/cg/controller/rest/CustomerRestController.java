package com.cg.controller.rest;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import com.cg.model.dto.CustomerCreReqDTO;
import com.cg.model.dto.CustomerResDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerRestController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> findById(@PathVariable Long customerId) {
        Customer customer = customerService.findById(customerId);
        CustomerResDTO customerResDTO = customer.toCustomerResDTO();
        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody CustomerCreReqDTO customerCreReqDTO, BindingResult bindingResult) {
        new CustomerCreReqDTO().validate(customerCreReqDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Customer customer = customerCreReqDTO.toCustomer();
        customer.setBalance(BigDecimal.ZERO);
        customer.setDeleted(false);

        customerService.create(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

}
