package com.example.kafka.inventory.infra.adapter.out.persistance;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "inventory")
@Table(name = "inventory")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "productid")
    private int productId;
    private int quantity;

}
