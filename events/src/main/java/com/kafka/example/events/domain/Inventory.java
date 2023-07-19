package com.kafka.example.events.domain;

import lombok.Data;

@Data
public class Inventory {

    private int id;
    private int productId;
    private int quantity;

    public void updateInventory(Integer orderQuantity) {
        quantity = quantity - orderQuantity;
    }

}
