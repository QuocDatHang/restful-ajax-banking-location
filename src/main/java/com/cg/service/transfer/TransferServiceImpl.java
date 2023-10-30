package com.cg.service.transfer;

import com.cg.model.Transfer;
import com.cg.repository.ITransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
@Transactional
public class TransferServiceImpl implements ITransferService{
    @Autowired
    private ITransferRepository transferRepository;
    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer findById(Long id) {
        return null;
    }

    @Override
    public void save(Transfer transfer) {
        transfer.setDeleted(false);
        transfer.setCreateAt(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
        transferRepository.save(transfer);
    }

    @Override
    public void delete(Long id) {

    }
}
