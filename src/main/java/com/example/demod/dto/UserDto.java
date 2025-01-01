package com.example.demod.dto;
import java.util.Set;

import com.example.demod.common.role;
import com.example.demod.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String fullName;
    private String userEmail;
    private String userPassword;
    private role userRole;
}
