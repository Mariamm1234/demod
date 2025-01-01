package com.example.demod.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import com.example.demod.dto.AddToCartDto;
import com.example.demod.dto.CartDto;
import com.example.demod.entities.Cart;
import com.example.demod.entities.Product;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.services.Cart.CartService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@RestController
@Transactional
@RequestMapping("/api/cart")
public class CartController {

CartService cartService;
@Async
@PostMapping(path = "/addtocart")
public CompletableFuture<ResponseEntity<CartDto>> addToCart(@RequestBody AddToCartDto cartItem

) {
   try {
    return CompletableFuture.supplyAsync(()->{
CartDto cr=cartService.addToCart(cartItem.getUserEmail(), cartItem.getProductId());
return new ResponseEntity<>(cr,HttpStatus.ACCEPTED);
    });
   } catch (Exception e) {
   throw new EtAuthException(e.getMessage());
   }
}


@Async
@DeleteMapping(path = "/deletecart/{userEmail}/{productId}",consumes = MediaType.ALL_VALUE)
public CompletableFuture<ResponseEntity<Cart>> deleteFromCart(
    @PathVariable String userEmail,
    @PathVariable Integer productId
    
)
{
    try {
        return CompletableFuture.supplyAsync(()->{
Cart cr=cartService.deleteFromCart(userEmail, productId);
return new ResponseEntity<>(cr,HttpStatus.GONE);
        });
    } catch (Exception e) {
        throw new EtAuthException(e.getMessage());
    }
}

}
