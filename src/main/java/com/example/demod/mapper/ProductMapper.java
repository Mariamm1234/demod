package com.example.demod.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.demod.dto.ProductDto;
import com.example.demod.entities.Product;
import com.example.demod.entities.User;

public class ProductMapper {

    public static ProductDto mapToProductDto(Product pr) {

        return new ProductDto(
                pr.getProductId(),
                pr.getProductName(),
                pr.getDescription(),
                pr.getPrice(),
                "created");
    }
}
