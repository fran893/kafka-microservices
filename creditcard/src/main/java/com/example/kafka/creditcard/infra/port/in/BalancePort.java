package com.example.kafka.creditcard.infra.port.in;

import com.kafka.example.events.domain.CreditCardBalance;

import java.util.List;

public interface BalancePort {

    List<CreditCardBalance> getBalances();
    CreditCardBalance getBalance(int customerId);

}
