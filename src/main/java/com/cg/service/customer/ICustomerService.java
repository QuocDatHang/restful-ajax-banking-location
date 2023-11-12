package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.model.dto.CustomerUpReqDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    void create(Customer customer);
    void update(Long customerId, Long locationRegionId, CustomerUpReqDTO customerUpReqDTO);

    void deposit(Deposit deposit);
    void withdraw(Withdraw withdraw);
    void transfer(Transfer transfer);

    List<Customer> findAllWithoutId(Long id);
//    List<CustomerResDTO> findAllCustomerResDTO();
}
