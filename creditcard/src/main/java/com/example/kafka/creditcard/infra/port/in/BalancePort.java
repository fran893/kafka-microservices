package com.example.kafka.creditcard.infra.port.in;

import com.kafka.example.events.domain.CreditCardBalance;
import com.kafka.example.events.domain.Order;

import java.util.List;

public interface BalancePort {

    List<CreditCardBalance> getBalances();
    CreditCardBalance getBalance(int customerId);

    void updateBalance(Order order);

}
