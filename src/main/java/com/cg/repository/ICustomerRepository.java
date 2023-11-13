package com.cg.repository;

import com.cg.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByIdNotAndDeletedFalse(Long id);

    List<Customer> findCustomersByDeletedIsFalse();

    @Modifying
    @Query("UPDATE Customer AS cus " +
            "SET cus.balance = cus.balance + :transactionAmount " +
            "WHERE cus.id = :customerId"
    )
    void incrementBalance(@Param("customerId") Long customerId, @Param("transactionAmount") BigDecimal transactionAmount);

    @Modifying
    @Query("UPDATE Customer AS cus " +
            "SET cus.balance = cus.balance - :transactionAmount " +
            "WHERE cus.id = :customerId"
    )
    void decrementBalance(@Param("customerId") Long customerId, @Param("transactionAmount") BigDecimal transactionAmount);
}
