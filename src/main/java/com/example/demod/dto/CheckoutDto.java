package com.example.demod.dto;

import java.util.List;

import com.example.demod.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {

    private List<Product> product;
    private Double total;
    private String taxPercent;
    private Double taxTotal;
}
