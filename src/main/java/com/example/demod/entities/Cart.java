package com.example.demod.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private Integer cartId;
    @Column(name = "amount")
    private Integer numberOfPieces;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "user_id")
    private Integer userId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cart_item", // Join table name
            joinColumns = @JoinColumn(name = "cart_id"), // Foreign key in join table for Product
            inverseJoinColumns = @JoinColumn(name = "product_id") // Foreign key in join table for Vendor
    )
    private List<Product> products;
}
