package com.example.kafka.orders.infra.port.in;


import com.kafka.example.events.domain.Order;

import java.util.List;

public interface OrderPort {

    List<Order> getOrders();
    void saveOrder(Order order);

}
