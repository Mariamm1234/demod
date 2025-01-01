package com.example.demod.entities;

import java.time.LocalDateTime;

import com.example.demod.common.status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer transaction_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private status type;
    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User vendor;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "occurance_time")
    private LocalDateTime transactionDate;

}
