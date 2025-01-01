package com.example.demod.services.Transaction;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demod.common.status;
import com.example.demod.dto.TransactionDto;
import com.example.demod.entities.Transaction;
import com.example.demod.exceptions.EtAuthException;

public interface TransactionService {
    Map<String, Double> getVendorAccountDetails(String email) throws EtAuthException;

    Transaction addTransaction(TransactionDto tracDto) throws EtAuthException;
}
