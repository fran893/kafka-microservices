package com.example.kafka.orders.application;

import com.example.kafka.orders.infra.adapter.out.persistance.IMapper;
import com.example.kafka.orders.infra.adapter.out.persistance.OrderEntity;
import com.example.kafka.orders.infra.port.in.OrderPort;
import com.example.kafka.orders.infra.port.out.OrderRepository;
import com.kafka.example.events.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderPort {

    private OrderRepository orderRepository;
    private IMapper<Order, OrderEntity> mapper;
    private OrderEventService orderEventService;

    @Autowired
    public OrderService(OrderRepository orderRepository, IMapper<Order, OrderEntity> mapper, OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.orderEventService = orderEventService;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();

        orderRepository.findAll().forEach(orderEntity -> {
            orders.add(mapper.entityToDomain(orderEntity));
        });

        return orders;
    }

    @Override
    public void saveOrder(Order order) {
        OrderEntity orderEntity = mapper.domainToEntity(order);

        orderRepository.save(orderEntity);

        orderEventService.publish(order);
    }
}
