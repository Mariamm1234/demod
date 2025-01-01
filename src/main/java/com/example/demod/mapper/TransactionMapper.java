package com.example.demod.mapper;

import com.example.demod.dto.TransactionDto;
import com.example.demod.entities.Transaction;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(Transaction trac) {
        return new TransactionDto(
                trac.getVendor().getUserEmail(),
                trac.getProduct().getProductId(),
                trac.getType(),
                trac.getPrice());
    }
}
