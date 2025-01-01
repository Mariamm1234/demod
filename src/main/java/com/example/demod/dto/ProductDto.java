package com.example.demod.dto;

import java.util.List;
import java.util.Set;

import com.example.demod.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
private String name;
private String description;
private Double price;
private String message;
}
