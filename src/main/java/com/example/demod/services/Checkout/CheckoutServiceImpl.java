package com.example.demod.services.Checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demod.dto.CheckoutDto;
import com.example.demod.entities.Cart;
import com.example.demod.entities.Checkout;
import com.example.demod.entities.User;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.mapper.CheckoutMapper;
import com.example.demod.repositories.CartRepository;
import com.example.demod.repositories.CheckoutRepository;
import com.example.demod.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    CheckoutRepository checkoutRepo;

    @Autowired
    CartRepository cartRepo;

    @Autowired
    UserRepository userRepo;

    @Transactional
    @Override
    public CheckoutDto addCheckout(Integer cartId) throws EtAuthException {
        try {
            // Get cart that has products to be out
            Cart cart = cartRepo.findCartById(cartId);
            if (cart == null)
                throw new EtAuthException("No cart bt this id:" + cartId);

            // Get user of the cart
            User us = userRepo.findUserById(cart.getUserId());
            if (us == null)
                throw new EtAuthException("Register now!!");

            // Create new checkout record to save the processe
            Checkout check = new Checkout();
            check.setCart(cart);
            check.setTaxPercent("12%");
            Double tax = cart.getTotalPrice() + (cart.getTotalPrice() * 0.12);
            check.setTotal(tax);
            check.setTaxTotal(cart.getTotalPrice() * 0.12);
            check.setUser(us);
            checkoutRepo.save(check);

            // Response in simpler form
            return CheckoutMapper.mapToCheckoutDto(check);
        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }
    }

}
