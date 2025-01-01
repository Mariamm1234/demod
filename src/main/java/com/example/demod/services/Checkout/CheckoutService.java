package com.example.demod.services.Checkout;

import org.springframework.stereotype.Service;

import com.example.demod.dto.CheckoutDto;
import com.example.demod.entities.Checkout;
import com.example.demod.exceptions.EtAuthException;

public interface CheckoutService {
    CheckoutDto addCheckout(Integer cartId) throws EtAuthException;
}
