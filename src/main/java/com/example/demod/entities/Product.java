package com.example.demod.entities;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.mapping.ManyToOne;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_type")
    private String productType;
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "product_price")
    private Double price;

    @Column(name = "descrip")
    private String description;
    @Column(name = "vendor_id")

    private Integer vendorId;

}
