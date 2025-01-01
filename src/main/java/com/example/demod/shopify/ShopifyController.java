package com.example.demod.shopify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demod.entities.Product;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.repositories.ProductRepository;
import com.example.demod.services.Product.ProductService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/shopify")
public class ShopifyController {
    private final ShopifyServiceImpl shopifyService;
    @Autowired
    ProductService productRepo;

    @GetMapping("/products")
    public String getProducts() {
        return shopifyService.getProducts();
    }

    @PostMapping("/products")
    public String createProduct(@RequestBody String productJson) {
        return shopifyService.createProduct(productJson);
    }

    @PostMapping("/add-product/{userId}")
    public ResponseEntity<String> addProduct(@RequestBody Product product, @PathVariable Integer userId) {
        try {
            return shopifyService.addProduct(product, userId);
        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            return shopifyService.deleteProduct(id);
        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }
    }

}
