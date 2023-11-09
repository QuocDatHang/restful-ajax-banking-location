package com.cg.controller.rest;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import com.cg.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<CustomerResDTO> create(@RequestBody Customer customer) {
//        Customer newCustomer = customerService.createCustomer(customer);
//        CustomerResDTO newCustomerResDTO = newCustomer.toCustomerResDTO();
//        return new ResponseEntity<>(newCustomerResDTO, HttpStatus.CREATED);
//    }

}
