package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.repository.IDepositRepository;
import com.cg.service.customer.CustomerServiceImpl;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.service.transfer.ITransferService;
import com.cg.service.transfer.TransferServiceImpl;
import com.cg.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDepositService depositService;
    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    private ITransferService transferService;

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/list";
    }

    @GetMapping("create")
    public String showCreate(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute Customer customer) {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        if (customer.getFullName().isEmpty()) {
            modelAndView.addObject("success", false);
            modelAndView.addObject("message", "Create Failed");
        } else {
            customerService.save(customer);
            modelAndView.addObject("success", true);
            modelAndView.addObject("message", "Create Successful");
        }
        return modelAndView;
    }

    @GetMapping("deposit/{customerId}")
    public String depositPage(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        Deposit deposit = new Deposit();

        if (customer == null) {
            model.addAttribute("checkValid", false);
            model.addAttribute("success", false);
            model.addAttribute("message", "Can not found customer");
        } else {
            model.addAttribute("checkValid", true);
            deposit.setCustomer(customer);
        }
        model.addAttribute("deposit", deposit);
        return "customer/deposit";
    }


    @PostMapping("deposit/{customerId}")
    public String deposit(@PathVariable Long customerId, @ModelAttribute Deposit deposit, Model model) {
        Customer customer = customerService.findById(customerId);
        deposit.setCustomer(customer);
        if ((deposit.getTransactionAmount() == null) ||
                (deposit.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0)) {
            model.addAttribute("success", false);
            model.addAttribute("checkValid", true);
            model.addAttribute("message", "Please input a number bigger 0");
            model.addAttribute("deposit", deposit);
            return "customer/deposit";
        } else {
            customerService.deposit(deposit);
            depositService.save(deposit);
            deposit.setTransactionAmount(null);
            model.addAttribute("deposit", deposit);
            model.addAttribute("success", true);
            model.addAttribute("checkValid", true);
            model.addAttribute("message", "Deposit successfully");
        }

        return "customer/deposit";
    }

    @GetMapping("withdraw/{customerId}")
    public String withdrawPage(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        Withdraw withdraw = new Withdraw();
        if (customer == null) {
            model.addAttribute("checkValid", false);
            model.addAttribute("success", false);
            model.addAttribute("message", "Can not found customer");
        } else {
            model.addAttribute("checkValid", true);
            withdraw.setCustomer(customer);
        }
        withdraw.setCustomer(customer);
        model.addAttribute("withdraw", withdraw);
        return "customer/withdraw";
    }

    @PostMapping("withdraw/{customerId}")
    public String withdraw(@PathVariable Long customerId, @ModelAttribute Withdraw withdraw, Model model) {
        Customer customer = customerService.findById(customerId);
        withdraw.setCustomer(customer);
        if ((withdraw.getTransactionAmount() == null) ||
                (withdraw.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0) ||
                (withdraw.getTransactionAmount().compareTo(customer.getBalance()) > 0)) {
            model.addAttribute("success", false);
            model.addAttribute("checkValid", false);
            model.addAttribute("message", "Please input a number bigger 0");
            model.addAttribute("deposit", withdraw);
            return "customer/withdraw";
        } else {
            customerService.withdraw(withdraw);
            withdrawService.save(withdraw);
            withdraw.setTransactionAmount(null);
            model.addAttribute("withdraw", withdraw);
            model.addAttribute("success", true);
            model.addAttribute("checkValid", true);
            model.addAttribute("message", "Withdraw successfully");
        }
        return "customer/withdraw";
    }

    @GetMapping("update/{customerId}")
    public String showUpdate(@PathVariable Long customerId, Model model) {
        model.addAttribute("customerEdit", customerService.findById(customerId));
        return "customer/update";
    }

    @PostMapping("update/{customerId}")
    public String update(@PathVariable Long customerId, @ModelAttribute Customer customerEdit, Model model) {
        Customer customer = customerService.findById(customerId);
        customerEdit.setBalance(customer.getBalance());
        customerEdit.setId(customerId);
        customerService.save(customerEdit);
        model.addAttribute("customerEdit", customerEdit);
        model.addAttribute("success", true);
        model.addAttribute("message", "Update successfully");
        return "customer/update";
    }

    @GetMapping("transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {
        Customer sender = customerService.findById(senderId);
        List<Customer> recipients = customerService.findAllWithoutId(senderId);
        Transfer transfer = new Transfer();
        transfer.setSender(sender);
        model.addAttribute("transfer", transfer);
        model.addAttribute("recipients", recipients);

        return "customer/transfer";
    }

    @PostMapping("transfer/{senderId}")
    public String transfer(@PathVariable Long senderId, Model model,
                           @ModelAttribute Transfer transfer, @RequestParam Long recipientId) {
        Customer sender = customerService.findById(senderId);
        Customer recipient = customerService.findById(recipientId);
        transfer.setFees(10L);
        List<Customer> recipients = customerService.findAllWithoutId(senderId);
        transfer.setSender(sender);

        if (transfer.getTransferAmount() == null ||
                transfer.getTransferAmount().compareTo(BigDecimal.ZERO) <= 0 ||
                transfer.getTransferAmount().multiply(BigDecimal.valueOf(1.1)).compareTo(sender.getBalance()) > 0) {

            model.addAttribute("transfer", transfer);
            model.addAttribute("recipients", recipients);
            model.addAttribute("success", false);
            model.addAttribute("message", "Transfer failed");
            return "customer/transfer";
        } else {
            transfer.setFeesAmount(transfer.getTransferAmount().multiply(BigDecimal.valueOf(0.1)));
            transfer.setTransactionAmount(transfer.getTransferAmount().multiply(BigDecimal.valueOf(1.1)));

            sender.setBalance(sender.getBalance().subtract(transfer.getTransactionAmount()));
            transfer.setSender(sender);
            customerService.save(sender);
            recipient.setBalance(recipient.getBalance().add(transfer.getTransferAmount()));
            transfer.setRecipient(recipient);
            customerService.save(recipient);
            transferService.save(transfer);

            transfer.setTransferAmount(BigDecimal.ZERO);
            model.addAttribute("transfer", transfer);
            model.addAttribute("recipients", recipients);
            model.addAttribute("success", true);
            model.addAttribute("message", "Transfer successfully");
        }
        return "customer/transfer";
    }

    @GetMapping("transferHistories")
    public String transferHistories(Model model) {
        model.addAttribute("transfers", transferService.findAll());
        return "customer/transfer_histories";
    }

    @GetMapping("delete/{customerId}")
    public String delete(Model model, @PathVariable Long customerId, RedirectAttributes redirectAttributes) {
        customerService.delete(customerId);
        return "redirect:/customers";
    }
}
