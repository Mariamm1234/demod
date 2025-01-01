package com.example.demod.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demod.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c WHERE c.userId=:userId")
    Cart findCartByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM Cart c WHERE c.cartId=:cartId")
    Cart findCartById(@Param("cartId") Integer cartId);
}
