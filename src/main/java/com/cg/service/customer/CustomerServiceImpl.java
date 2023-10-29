package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements ICustomerService{
    private static final List<Customer> customers = new ArrayList<>();
    private static Long id = 1L;
    static {
        customers.add(new Customer(id++, "Nam", "namtran@gmail.com", "0122323231", "12 Le Loi", BigDecimal.ZERO, false));
        customers.add(new Customer(id++, "Tuan", "tuanle@gmail.com", "8222366741", "1242 Nguyen Hue", BigDecimal.ZERO, false));
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }

    @Override
    public Customer findById(Long id) {
        return customers.stream().filter(customer -> customer.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void create(Customer customer) {
        customer.setId(id++);
        customer.setBalance(BigDecimal.ZERO);
        customer.setDeleted(false);
        customers.add(customer);
    }

    @Override
    public void update(Long id, Customer customer) {
        int index = customers.indexOf(findById(id));
        customer.setDeleted(false);
        customers.set(index, customer);
    }

    @Override
    public void delete(Long id) {

    }
    public void deposit(Deposit deposit) {
        Customer customer = deposit.getCustomer();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        BigDecimal newBalance = currentBalance.add(transactionAmount);
        customer.setBalance(newBalance);
//        update(customer.getId(), customer);
    }
    public void withdraw(Withdraw withdraw) {
        Customer customer = withdraw.getCustomer();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        BigDecimal newBalance = currentBalance.subtract(transactionAmount);
        customer.setBalance(newBalance);
        update(customer.getId(), customer);
    }

    @Override
    public List<Customer> findAllWithoutId(Long id) {
        return customers.stream().filter(customer -> !Objects.equals(customer.getId(), id)).collect(Collectors.toList());
    }
}
