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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    public ModelAndView create(@Valid @ModelAttribute Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("success", false);
            modelAndView.addObject("customer", customer);
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
        deposit.setCustomer(customer);

        if (customer == null) {
            model.addAttribute("checkValid", false);
            model.addAttribute("message", "Can not found customer");
        } else {
            model.addAttribute("checkValid", true);
        }
        model.addAttribute("deposit", deposit);
        return "customer/deposit";
    }

    @PostMapping("deposit/{customerId}")
    public String deposit(@Valid @PathVariable Long customerId, @ModelAttribute Deposit deposit, BindingResult bindingResult, Model model) {
        Customer customer = customerService.findById(customerId);
        deposit.setCustomer(customer);
        new Deposit().validate(deposit, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("success", false);
        } else {
            customerService.deposit(deposit);
            depositService.save(deposit);
            deposit.setTransactionAmount(null);
            model.addAttribute("success", true);
            model.addAttribute("message", "Deposit successfully");
        }
        model.addAttribute("checkValid", true);
        model.addAttribute("deposit", deposit);
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
    public String withdraw(@PathVariable Long customerId,  @ModelAttribute Withdraw withdraw, BindingResult bindingResult, Model model) {
        Customer customer = customerService.findById(customerId);
        withdraw.setCustomer(customer);
        new Withdraw().validate(withdraw, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("success", false);
        } else {
            customerService.withdraw(withdraw);
            withdrawService.save(withdraw);
            withdraw.setTransactionAmount(null);
            model.addAttribute("success", true);
            model.addAttribute("message", "Withdraw successfully");
        }
        model.addAttribute("withdraw", withdraw);
        model.addAttribute("checkValid", true);
        return "customer/withdraw";
    }

    @GetMapping("update/{customerId}")
    public String showUpdate(@PathVariable Long customerId, Model model) {
        model.addAttribute("customer", customerService.findById(customerId));
        return "customer/update";
    }

    @PostMapping("update/{customerId}")
    public String update(@Valid @PathVariable Long customerId, @ModelAttribute Customer customer, BindingResult bindingResult, Model model) {
        Customer oldCustomer = customerService.findById(customerId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("success", false);
            model.addAttribute("customerEdit", customer);
        } else {
            customer.setBalance(oldCustomer.getBalance());
            customer.setId(customerId);
            customerService.save(customer);
            model.addAttribute("customer", customer);
            model.addAttribute("success", true);
            model.addAttribute("message", "Update successfully");
        }
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
                           @ModelAttribute Transfer transfer,
                           @RequestParam Long recipientId, BindingResult bindingResult) {
        Customer sender = customerService.findById(senderId);
        Customer recipient = customerService.findById(recipientId);
        transfer.setFees(10L);
        List<Customer> recipients = customerService.findAllWithoutId(senderId);
        transfer.setSender(sender);
        new Transfer().validate(transfer, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("transfer", transfer);
            model.addAttribute("recipients", recipients);
            model.addAttribute("success", false);
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
