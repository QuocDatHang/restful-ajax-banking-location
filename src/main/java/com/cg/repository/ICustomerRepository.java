package com.cg.repository;

import com.cg.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByIdNot(Long id);
    List<Customer> findCustomersByDeletedIsFalse();
}
