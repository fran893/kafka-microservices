package com.example.kafka.orders.infra.adapter.out.persistance;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "customers")
@Table(name = "customers")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @Column(name = "productid")
    private int productId;
    private double price;
    @Column(name = "costumerid")
    private int customerId;

}
