package com.example.demod.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.example.demod.dto.TransactionDto;
import com.example.demod.entities.Transaction;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.services.Transaction.TransactionService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@AllArgsConstructor
@Transactional
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @Async
    @GetMapping(path = "/vendortransaction/{param}", consumes = MediaType.ALL_VALUE)
    public CompletableFuture<ResponseEntity<Map<String, Double>>> getMethodName(@PathVariable String param) {

        try {
            return CompletableFuture.supplyAsync(()->{
                Map<String, Double> res=transactionService.getVendorAccountDetails(param);
                return new ResponseEntity<>(res,HttpStatus.OK);
            })  ;    
          } catch (Exception e) {
              Map<String, Double> errorResponse = new HashMap<>();
        errorResponse.put("error", -1.0); // Use -1.0 or another placeholder value
        return CompletableFuture.completedFuture(
            new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND)
        );  
     }
        }

        @Async
        @PostMapping(path = "/addtransaction" ,consumes = MediaType.ALL_VALUE)
        public CompletableFuture<ResponseEntity<Transaction>> addTransaction(@RequestBody TransactionDto tracDto) {
         try {
            return CompletableFuture.supplyAsync(()->{
Transaction trac=transactionService.addTransaction(tracDto);
return new ResponseEntity<>(trac,HttpStatus.CREATED);
            });
         } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
         }
        }
        
    
}
