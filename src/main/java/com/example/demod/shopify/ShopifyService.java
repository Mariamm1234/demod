package com.example.demod.shopify;

import org.springframework.http.ResponseEntity;

import com.example.demod.entities.Product;

public interface ShopifyService {

    public String getProducts();

    public String createProduct(String productJson);

    public ResponseEntity<String> addProduct(Product product, Integer userId);

    public ResponseEntity<String> deleteProduct(Long productId);
}
