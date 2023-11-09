package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("customers")
public class CustomerController {

    @GetMapping()
    public String showAll(Model model) {
        return "customer/list";
    }

//    @GetMapping("create")
//    public String showCreate(Model model) {
//        model.addAttribute("customer", new Customer());
//        return "customer/create";
//    }
//
//    @PostMapping("create")
//    public ModelAndView create(@Valid @ModelAttribute Customer customer, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView("customer/create");
//        if (bindingResult.hasErrors()) {
//            modelAndView.addObject("success", false);
//            modelAndView.addObject("customer", customer);
//        } else {
//            customerService.save(customer);
//            modelAndView.addObject("success", true);
//            modelAndView.addObject("message", "Create Successful");
//        }
//        return modelAndView;
//    }
//
//    @GetMapping("deposit/{customerId}")
//    public String depositPage(@PathVariable Long customerId, Model model) {
//        Customer customer = customerService.findById(customerId);
//        Deposit deposit = new Deposit();
//        if (customer == null) {
//            deposit = null;
//        }
//        deposit.setCustomer(customer);
//        model.addAttribute("checkValid", true);
//        model.addAttribute("deposit", deposit);
//        return "customer/deposit";
//    }
//
//    @PostMapping("deposit/{customerId}")
//    public String deposit(@Valid @PathVariable Long customerId, @ModelAttribute Deposit deposit, BindingResult bindingResult, Model model) {
//        Customer customer = customerService.findById(customerId);
//        deposit.setCustomer(customer);
//        new Deposit().validate(deposit, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("success", false);
//        } else {
//            customerService.deposit(deposit);
//            depositService.save(deposit);
//            deposit.setTransactionAmount(null);
//            model.addAttribute("success", true);
//            model.addAttribute("message", "Deposit successfully");
//        }
//        model.addAttribute("deposit", deposit);
//        return "customer/deposit";
//    }
//
//    @GetMapping("withdraw/{customerId}")
//    public String withdrawPage(@PathVariable Long customerId, Model model) {
//        Customer customer = customerService.findById(customerId);
//        Withdraw withdraw = new Withdraw();
//        if (customer == null) {
//            withdraw = null;
//        }
//        withdraw.setCustomer(customer);
//        withdraw.setCustomer(customer);
//        model.addAttribute("withdraw", withdraw);
//        return "customer/withdraw";
//    }
//
//    @PostMapping("withdraw/{customerId}")
//    public String withdraw(@PathVariable Long customerId, @ModelAttribute Withdraw withdraw, BindingResult bindingResult, Model model) {
//        Customer customer = customerService.findById(customerId);
//        withdraw.setCustomer(customer);
//        new Withdraw().validate(withdraw, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("success", false);
//        } else {
//            customerService.withdraw(withdraw);
//            withdrawService.save(withdraw);
//            withdraw.setTransactionAmount(null);
//            model.addAttribute("success", true);
//            model.addAttribute("message", "Withdraw successfully");
//        }
//        model.addAttribute("withdraw", withdraw);
//        return "customer/withdraw";
//    }
//
//    @GetMapping("update/{id}")
//    public String showUpdate(@PathVariable Long id, Model model) {
//        model.addAttribute("customer", customerService.findById(id));
//        return "customer/update";
//    }
//
//    @PostMapping("update/{id}")
//    public String update(@Valid @ModelAttribute Customer customer, BindingResult bindingResult, Model model) {
//        Customer oldCustomer = customerService.findById(customer.getId());
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("success", false);
//            model.addAttribute("customer", customer);
//        } else {
//            customer.setBalance(oldCustomer.getBalance());
//            customerService.save(customer);
//            model.addAttribute("customer", customer);
//            model.addAttribute("success", true);
//            model.addAttribute("message", "Update successfully");
//        }
//        return "customer/update";
//    }
//
//    @GetMapping("transfer/{senderId}")
//    public String showTransferPage(@PathVariable Long senderId, Model model) {
//        Customer sender = customerService.findById(senderId);
//        List<Customer> recipients = customerService.findAllWithoutId(senderId);
//        Transfer transfer = new Transfer();
//        if (sender == null) {
//            transfer = null;
//        }
//        transfer.setSender(sender);
//        model.addAttribute("transfer", transfer);
//        model.addAttribute("recipients", recipients);
//
//        return "customer/transfer";
//    }
//
//    @PostMapping("transfer/{senderId}")
//    public String transfer(@PathVariable Long senderId, Model model,
//                           @ModelAttribute Transfer transfer,
//                           @RequestParam Long recipientId, BindingResult bindingResult) {
//        Customer sender = customerService.findById(senderId);
//        Customer recipient = customerService.findById(recipientId);
//        transfer.setFees(10L);
//        List<Customer> recipients = customerService.findAllWithoutId(senderId);
//        transfer.setSender(sender);
//        new Transfer().validate(transfer, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("transfer", transfer);
//            model.addAttribute("recipients", recipients);
//            model.addAttribute("success", false);
//        } else {
//            transfer.setFeesAmount(transfer.getTransferAmount().multiply(BigDecimal.valueOf(0.1)));
//            transfer.setTransactionAmount(transfer.getTransferAmount().multiply(BigDecimal.valueOf(1.1)));
//
//            sender.setBalance(sender.getBalance().subtract(transfer.getTransactionAmount()));
//            transfer.setSender(sender);
//            customerService.save(sender);
//            recipient.setBalance(recipient.getBalance().add(transfer.getTransferAmount()));
//            transfer.setRecipient(recipient);
//            customerService.save(recipient);
//            transferService.save(transfer);
//
//            transfer.setTransferAmount(BigDecimal.ZERO);
//            model.addAttribute("transfer", transfer);
//            model.addAttribute("recipients", recipients);
//            model.addAttribute("success", true);
//            model.addAttribute("message", "Transfer successfully");
//        }
//        return "customer/transfer";
//    }
//
//    @GetMapping("transferHistories")
//    public String transferHistories(Model model) {
//        model.addAttribute("transfers", transferService.findAll());
//        return "customer/transfer_histories";
//    }
//
//    @GetMapping("delete/{customerId}")
//    public String delete(Model model, @PathVariable Long customerId, RedirectAttributes redirectAttributes) {
//        customerService.delete(customerId);
//        return "redirect:/customers";
//    }
}
