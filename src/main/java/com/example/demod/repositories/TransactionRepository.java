package com.example.demod.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demod.common.status;
import com.example.demod.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.vendor.id = :vendorId AND t.type = :type")
    List<Transaction> findByVendorAndType(@Param("vendorId") Integer vendorId, @Param("type") status type);

}
