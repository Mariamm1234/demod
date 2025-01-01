package com.example.demod.services.Cart;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demod.dto.CartDto;
import com.example.demod.entities.Cart;
import com.example.demod.entities.Product;
import com.example.demod.exceptions.EtAuthException;

public interface CartService {
    CartDto addToCart(String email, Integer productId) throws EtAuthException;

    Cart deleteFromCart(String email, Integer productId) throws EtAuthException;

}
