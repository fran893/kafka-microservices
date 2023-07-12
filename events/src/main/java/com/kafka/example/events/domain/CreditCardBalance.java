package com.kafka.example.events.domain;

import lombok.Data;

@Data
public class CreditCardBalance {

    private Long id;
    private double amount;
    private int customerId;

    public void updateAmount(double orderPrice) {
        amount = amount - orderPrice;
    }
}
