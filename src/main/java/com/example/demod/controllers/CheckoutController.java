package com.example.demod.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import com.example.demod.dto.CheckoutDto;
import com.example.demod.entities.Checkout;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.services.Checkout.CheckoutService;

import jakarta.el.ELException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@AllArgsConstructor
@Transactional
@RequestMapping("/api")
public class CheckoutController {

@Autowired
CheckoutService checkoutService;

@Async
@PostMapping(path = "/checkout/{cartId}")
public CompletableFuture<ResponseEntity<CheckoutDto>> addCheckout(@PathVariable Integer cartId ) {
    try {
        return CompletableFuture.supplyAsync(()->{
CheckoutDto check=checkoutService.addCheckout(cartId);
return new ResponseEntity<>(check,HttpStatus.CREATED);
        });
    } catch (Exception e) {
        throw new EtAuthException(e.getMessage());
    }
}


}
