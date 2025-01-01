package com.example.demod.services.Transaction;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demod.common.role;
import com.example.demod.common.status;
import com.example.demod.dto.TransactionDto;
import com.example.demod.entities.Product;
import com.example.demod.entities.Transaction;
import com.example.demod.entities.User;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.repositories.ProductRepository;
import com.example.demod.repositories.TransactionRepository;
import com.example.demod.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    ProductRepository productRepo;

    public Map<String, Double> getVendorAccountDetails(String email) {
        try {

            // Get vendor
            User vendor = userRepo.findUsersByEmail(email);

            if (vendor == null)
                throw new EtAuthException("Vendor not found with email: " + email);
            if (vendor.getUserRole().equals(role.CUSTOMER))
                throw new EtAuthException("Unauthorized");
            /*
             * Get all transaction that are | -->[vendor ,receivable ]
             *                              |-->[vendor ,payable]
             */
            List<Transaction> receivables = transactionRepository.findByVendorAndType(vendor.getUser_id(),
                    status.RECEIVABLE);
            List<Transaction> payables = transactionRepository.findByVendorAndType(vendor.getUser_id(), status.PAYABLE);

            Double totalReceivables = receivables.stream().mapToDouble(Transaction::getPrice).sum();
            Double totalPayables = payables.stream().mapToDouble(Transaction::getPrice).sum();

            Map<String, Double> accountDetails = new HashMap<>();
            accountDetails.put("totalReceivables", totalReceivables);
            accountDetails.put("totalPayables", totalPayables);

            return accountDetails;
        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Transaction addTransaction(TransactionDto tracDto)
            throws EtAuthException {
        try {

            // Get vendor
            User vendor = userRepo.findUsersByEmail(tracDto.getVendorEmail());
            if (vendor == null)
                throw new EtAuthException("Vendor not found with email: " + tracDto.getVendorEmail());

            // Get product
            Product pr = productRepo.findProductByID(tracDto.getProductId());
            if (pr == null)
                throw new EtAuthException("Product not found with ID: " + tracDto.getProductId());

            // Create new transaction
            Transaction trac = new Transaction();
            trac.setVendor(vendor);

            trac.setProduct(pr);

            if (tracDto.getType().equals(status.valueOf("RECEIVABLE")))
                trac.setType(status.RECEIVABLE);
            if (tracDto.getType().equals(status.valueOf("PAYABLE")))
                trac.setType(status.PAYABLE);

            trac.setPrice(tracDto.getPrice());

            trac.setTransactionDate(LocalDateTime.now());

            return transactionRepository.save(trac);
        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }
    }

}
