package com.example.demod.dto;

import com.example.demod.entities.Product;
import com.example.demod.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
private Product product;
}
