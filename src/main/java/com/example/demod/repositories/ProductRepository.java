package com.example.demod.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demod.entities.Product;
import com.example.demod.entities.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT u FROM User u WHERE u.fullName = :fullName")
    User findVendorByName(@Param("fullName") String fullName);

    @Query("SELECT p FROM Product p WHERE p.productId = :productId")
    Product findProductByID(@Param("productId") Integer productId);

}
