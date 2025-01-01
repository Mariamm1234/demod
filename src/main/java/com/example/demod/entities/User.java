package com.example.demod.entities;

import java.util.HashSet;
import java.util.Set;

import com.example.demod.common.role;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;
    private String fullName;
    @Column(nullable = false, unique = true)
    private String userEmail;
    private String userPassword;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private role userRole;
    @Column(name = "\"token\"", nullable = false, unique = true)
    private String token;

}
