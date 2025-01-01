package com.example.demod.dto;

import java.util.List;

import com.example.demod.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
private List<Product> products;
private Integer numberOfPieces;
private Double totalPrice;
}
