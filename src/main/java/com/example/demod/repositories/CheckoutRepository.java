package com.example.demod.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demod.entities.Checkout;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {
}
