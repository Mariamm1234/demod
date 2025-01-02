package com.example.demod.controllers;


import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import com.example.demod.dto.ProductDto;
import com.example.demod.dto.ProductRequestDto;
import com.example.demod.entities.Product;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.mapper.ProductMapper;
import com.example.demod.services.Product.ProductService;
import com.example.demod.shopify.ShopifyService;

import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;





@AllArgsConstructor
@RestController
@Transactional
@RequestMapping("/api/products")
public class ProductController {


    @Autowired
    ProductService productService;

    @Autowired
    ShopifyService shopifyService;
    @Async
    @GetMapping(path = "/allproducts")

   public CompletableFuture<ResponseEntity<ArrayList<ProductDto>>> getAllProducrs() throws EtAuthException
   {
    try {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<ProductDto>pr=productService.getProducts();
            //shopifyService.getProducts();
            if(pr.isEmpty())
            throw new EtAuthException("No products!!");
            return new ResponseEntity<>(pr,HttpStatus.OK);
        });
    } catch (Exception e) {
        throw new EtAuthException(e.getMessage());
    }
   }



   @Async
   @PostMapping(value="/addproduct/{userEmail}",
   consumes = MediaType.ALL_VALUE
 )
   public CompletableFuture<ResponseEntity<ProductDto>> addProduct(@RequestBody Product product,
   @PathVariable String userEmail
   )
   {
    try {
        return CompletableFuture.supplyAsync(()->{
         ProductDto pr=productService.addProduct(product,userEmail);
         if(pr.getMessage().equals("exist"))
         return new ResponseEntity<> (pr,HttpStatus.CONFLICT);
         else if(pr.getMessage().equals("new vendor"))
            return new ResponseEntity<> (pr,HttpStatus.CONTINUE);
            //shopifyService.addProduct(pr,pr.vendorId);
         return new ResponseEntity<> (pr,HttpStatus.CREATED);


        });
    } catch (Exception e) {
        throw new EtAuthException(e.getMessage());
    }
   }
   
   
   @Async
   @Transactional
   @DeleteMapping(path = "/deleteproduct/{email}/{productId}", consumes = MediaType.ALL_VALUE)
   public CompletableFuture<ResponseEntity<String>> deleteProduct(
       @PathVariable Integer productId,
       @PathVariable String email
   ) {
       return CompletableFuture.supplyAsync(() -> {
           try {
               String message = productService.deleteProduct(email, productId);
               //shopifyService.deleteProduct(id);
               return ResponseEntity.status(HttpStatus.GONE).body(message);
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
           }
       });
   }


   @Async
   @Transactional
   @PutMapping(path = "/editproduct/{productId}", consumes = MediaType.ALL_VALUE)
   public CompletableFuture<ResponseEntity<Product>> editProduct(@RequestBody Product product,
   @PathVariable Integer productId
   ) {
       try {
        return CompletableFuture.supplyAsync(()->{
            Product pr=productService.editProduct(product, productId);
            return new ResponseEntity<>(pr,HttpStatus.OK);
        });
       } catch (Exception e) {
        throw new EtAuthException(e.getMessage());
       }
   }
   

}
