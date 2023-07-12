package com.example.kafka.orders.infra.adapter.out.persistance;

import com.kafka.example.events.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements IMapper<Order, OrderEntity>{

    @Override
    public Order entityToDomain(OrderEntity orderEntity) {
        Order order = new Order();

        order.setOrderId(orderEntity.getOrderId());
        order.setCustomerId(orderEntity.getOrderId());
        order.setPrice(orderEntity.getPrice());
        order.setCustomerId(orderEntity.getCustomerId());

        return order;
    }

    @Override
    public OrderEntity domainToEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setOrderId(order.getOrderId());
        orderEntity.setCustomerId(order.getOrderId());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setCustomerId(order.getCustomerId());

        return orderEntity;
    }
}
