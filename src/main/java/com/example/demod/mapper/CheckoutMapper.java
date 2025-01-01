package com.example.demod.mapper;

import java.util.List;

import org.hibernate.Hibernate;

import com.example.demod.dto.CheckoutDto;
import com.example.demod.entities.Checkout;
import com.example.demod.entities.Product;

public class CheckoutMapper {
    public static CheckoutDto mapToCheckoutDto(Checkout check) {
        List<Product> pr = check.getCart().getProducts();
        Hibernate.initialize(check.getCart().getProducts());
        return new CheckoutDto(
                pr,
                check.getTotal(),
                check.getTaxPercent(),
                check.getTaxTotal());
    }

}
