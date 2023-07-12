package com.example.kafka.creditcard.infra.adapter.out.persistance;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "balances")
@Table(name = "balances")
public class CreditCardBalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    @Column(name = "customerid")
    private int customerId;

}
