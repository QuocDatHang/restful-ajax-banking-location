package com.cg.repository;

import com.cg.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByIdNotAndDeletedFalse(Long id);

    List<Customer> findCustomersByDeletedIsFalse();


}
