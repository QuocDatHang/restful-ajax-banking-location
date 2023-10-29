package com.cg.service.withdraw;

import com.cg.model.Withdraw;
import com.cg.repository.IWithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class WithdrawServiceImpl implements IWithdrawService {
    @Autowired
    private IWithdrawRepository withdrawRepository;

    @Override
    public List<Withdraw> findAll() {
        return null;
    }

    @Override
    public Withdraw findById(Long id) {
        return withdrawRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Withdraw withdraw) {
        withdraw.setDeleted(false);
        withdrawRepository.save(withdraw);
    }

    @Override
    public void delete(Long id) {

    }

}
