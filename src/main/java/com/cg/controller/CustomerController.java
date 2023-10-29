package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.customer.CustomerServiceImpl;
import com.cg.service.customer.ICustomerService;
import com.cg.service.transfer.ITransferService;
import com.cg.service.transfer.TransferServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CustomerController {
    private ICustomerService customerService = new CustomerServiceImpl();
    private ITransferService transferService = new TransferServiceImpl();

    @GetMapping("/customers")
    public String showAll(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/list";
    }
    @GetMapping("customers/create")
    public String showCreate(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @PostMapping("customers/create")
    public ModelAndView create(@ModelAttribute Customer customer) {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        if (customer.getFullName().isEmpty()) {
            modelAndView.addObject("success", false);
            modelAndView.addObject("message", "Create Failed");
        } else {
            customerService.create(customer);
            modelAndView.addObject("success", true);
            modelAndView.addObject("message", "Create Successful");
        }
        return modelAndView;
    }

    @GetMapping("customers/deposit/{id}")
    public String depositPage(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        Deposit deposit = new Deposit();
        deposit.setCustomer(customer);
        model.addAttribute("deposit", deposit);
        return "customer/deposit";
    }


    @PostMapping("customers/deposit/{customerId}")
    public String deposit(@PathVariable Long customerId, @ModelAttribute Deposit deposit, Model model) {
        Customer customer = customerService.findById(customerId);
        deposit.setCustomer(customer);
        customerService.deposit(deposit);
        deposit.setTransactionAmount(null);
        model.addAttribute("deposit", deposit);
        model.addAttribute("success", true);
        model.addAttribute("message", "Deposit successfully");
        return "customer/deposit";
    }

    @GetMapping("customers/withdraw/{id}")
    public String withdrawPage(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customer);
        model.addAttribute("withdraw", withdraw);
        return "customer/withdraw";
    }

    @PostMapping("customers/withdraw/{customerId}")
    public String withdraw(@PathVariable Long customerId, @ModelAttribute Withdraw withdraw, Model model) {
        Customer customer = customerService.findById(customerId);
        withdraw.setCustomer(customer);
        customerService.withdraw(withdraw);
        withdraw.setTransactionAmount(null);
        model.addAttribute("withdraw", withdraw);
        model.addAttribute("success", true);
        model.addAttribute("message", "Withdraw successfully");
        return "customer/withdraw";
    }

    @GetMapping("customers/update/{customerId}")
    public String showUpdate(@PathVariable Long customerId, Model model) {
        model.addAttribute("customerEdit", customerService.findById(customerId));
        return "customer/update";
    }

    @PostMapping("customers/update/{customerId}")
    public String update(@PathVariable Long customerId, @ModelAttribute Customer customerEdit, Model model) {
        Customer customer = customerService.findById(customerId);
        customerEdit.setBalance(customer.getBalance());
        customerEdit.setId(customerId);
        customerService.update(customerId, customerEdit);
        model.addAttribute("customerEdit", customerEdit);
        model.addAttribute("success", true);
        model.addAttribute("message", "Update successfully");
        return "customer/update";
    }

    @GetMapping("customers/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {
        Customer sender = customerService.findById(senderId);
        List<Customer> recipients = customerService.findAllWithoutId(senderId);
        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        model.addAttribute("transfer", transfer);
        model.addAttribute("recipients", recipients);

        return "customer/transfer";
    }

    @PostMapping("/customers/transfer/{senderId}")
    public String transfer(@PathVariable Long senderId, Model model,
                           @ModelAttribute Transfer transfer, @RequestParam Long recipientId) {
        Customer sender = customerService.findById(senderId);
        Customer recipient = customerService.findById(recipientId);
        transfer.setTransactionAmount(transfer.getTransferAmount().multiply(BigDecimal.valueOf(1.1)));
        transferService.create(transfer);
        model.addAttribute("success", true);
        model.addAttribute("message", "Transfer successfully");
        return "customer/transfer";
    }
}
