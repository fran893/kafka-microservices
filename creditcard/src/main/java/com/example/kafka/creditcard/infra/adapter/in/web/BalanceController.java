package com.example.kafka.creditcard.infra.adapter.in.web;

import com.example.kafka.creditcard.infra.port.in.BalancePort;
import com.kafka.example.events.domain.CreditCardBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1")
public class BalanceController {

    private BalancePort balancePort;

    @Autowired
    public BalanceController(BalancePort balancePort) {
        this.balancePort = balancePort;
    }

    @GetMapping(value = "/balances")
    public ResponseEntity<List<CreditCardBalance>> getBalances() {
        return new ResponseEntity<>(balancePort.getBalances(), HttpStatus.OK);
    }

    @GetMapping(value = "/balances/{customer-id}")
    public ResponseEntity<CreditCardBalance> getBalanceByCustomer(@PathVariable(name = "customer-id") Integer customerId) {
        return new ResponseEntity<>(balancePort.getBalance(customerId), HttpStatus.OK);
    }
}
