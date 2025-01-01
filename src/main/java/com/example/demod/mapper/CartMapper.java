package com.example.demod.mapper;

import com.example.demod.dto.CartDto;
import com.example.demod.entities.Cart;

public class CartMapper {
    public static CartDto mapToCartDto(Cart cart) {
        return new CartDto(
                cart.getProducts(),
                cart.getNumberOfPieces(),
                cart.getTotalPrice()

        );
    }
}
