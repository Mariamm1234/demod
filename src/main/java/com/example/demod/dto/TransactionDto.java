package com.example.demod.dto;

import com.example.demod.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String vendorEmail;
    private Integer productId;
    private status type;
    private Double price;
}
