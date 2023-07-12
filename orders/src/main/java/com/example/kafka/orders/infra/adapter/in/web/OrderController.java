package com.example.kafka.orders.infra.adapter.in.web;

import com.example.kafka.orders.infra.port.in.OrderPort;
import com.kafka.example.events.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class OrderController {

    private OrderPort orderPort;

    @Autowired
    public OrderController(OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(orderPort.getOrders(), HttpStatus.OK);
    }

    @PostMapping(value = "/order")
    public void saveCustomer(@RequestBody Order order) {
        orderPort.saveOrder(order);
    }
}
