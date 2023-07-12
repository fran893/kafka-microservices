package com.kafka.example.events.domain;

import lombok.Data;

@Data
public class Order {

    private int orderId;
    private int productId;
    private double price;
    private int customerId;

}
