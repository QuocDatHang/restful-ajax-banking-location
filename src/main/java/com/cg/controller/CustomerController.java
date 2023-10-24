package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.customer.CustomerServiceImpl;
import com.cg.service.customer.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {
    private ICustomerService customerService = new CustomerServiceImpl();
    @GetMapping("/customers")
    public String showAll(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/list";
    }
}
