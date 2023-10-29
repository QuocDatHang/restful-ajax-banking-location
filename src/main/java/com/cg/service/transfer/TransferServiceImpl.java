package com.cg.service.transfer;

import com.cg.model.Deposit;
import com.cg.model.Transfer;

import java.util.ArrayList;
import java.util.List;

public class TransferServiceImpl implements ITransferService{
    private final static List<Transfer> transfers = new ArrayList<>();

    private static Long id;
    @Override
    public List<Transfer> findAll() {
        return null;
    }

    @Override
    public Transfer findById(Long id) {
        return null;
    }

    @Override
    public void create(Transfer transfer) {
        transfer.setId(id++);
        transfer.setDeleted(false);
        transfers.add(transfer);
    }

    @Override
    public void update(Long id, Transfer transfer) {

    }

    @Override
    public void delete(Long id) {

    }
}
