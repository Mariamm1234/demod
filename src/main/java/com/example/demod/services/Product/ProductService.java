package com.example.demod.services.Product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;

import com.example.demod.dto.ProductDto;
import com.example.demod.dto.ProductRequestDto;
import com.example.demod.entities.Product;
import com.example.demod.exceptions.EtAuthException;

public interface ProductService {
    ArrayList<ProductDto> getProducts() throws EtAuthException;

    ProductDto addProduct(Product product, String email) throws EtAuthException;

    String deleteProduct(String email, Integer productId) throws EtAuthException;

    Product editProduct(Product prouct, Integer productId) throws EtAuthException;
}
