package com.cg.service.customer;

import com.cg.model.*;

import com.cg.repository.ICustomerRepository;

import com.cg.repository.ILocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;
    private ILocationRegionRepository locationRegionRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findCustomersByDeletedIsFalse();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void create(Customer customer) {
        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegionRepository.save(locationRegion);

        customer.setLocationRegion(locationRegion);
        customerRepository.save(customer);
    }

    @Override
    public void save(Customer customer) {
        if (customer.getBalance() == null) {
            customer.setBalance(BigDecimal.ZERO);
        }
        customer.setDeleted(false);
        customerRepository.save(customer);
    }

    @Override
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        assert customer != null;
        customer.setDeleted(true);
    }

    public void deposit(Deposit deposit) {
        Customer customer = deposit.getCustomer();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        BigDecimal newBalance = currentBalance.add(transactionAmount);
        customer.setBalance(newBalance);
        customerRepository.save(customer);
    }

    public void withdraw(Withdraw withdraw) {
        Customer customer = withdraw.getCustomer();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        BigDecimal newBalance = currentBalance.subtract(transactionAmount);
        customer.setBalance(newBalance);
        customerRepository.save(customer);
    }

    @Override
    public void transfer(Transfer transfer) {

    }

    @Override
    public List<Customer> findAllWithoutId(Long id) {
        return customerRepository.findByIdNotAndDeletedFalse(id);
    }

//    @Override
//    public List<CustomerResDTO> findAllCustomerResDTO() {
//        return customerRepository.findAllCustomerResDTO();
//    }
}
