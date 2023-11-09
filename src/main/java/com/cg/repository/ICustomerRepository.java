package com.cg.repository;

import com.cg.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByIdNotAndDeletedFalse(Long id);

    List<Customer> findCustomersByDeletedIsFalse();
//    @Query("SELECT NEW com.cg.model.dto.response.CustomerResDTO (" +
//            "cus.id, " +
//            "cus.fullName, " +
//            "cus.email, " +
//            "cus.phone, " +
//            "cus.balance, " +
//            "cus.locationRegion" +
//            ") FROM Customer AS cus "
//    )

}
