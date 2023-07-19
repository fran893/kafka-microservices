package com.example.kafka.inventory.application;

import com.example.kafka.inventory.infra.port.in.InventoryPort;
import com.example.kafka.inventory.infra.port.in.MessagingEvent;
import com.kafka.example.events.domain.Event;
import com.kafka.example.events.domain.Order;
import com.kafka.example.events.domain.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderEventService implements MessagingEvent<Event<?>> {

    private InventoryPort inventoryPort;

    @Autowired
    public OrderEventService(InventoryPort inventoryPort) {
        this.inventoryPort = inventoryPort;
    }

    @KafkaListener(
            topics = {"${spring.kafka.consumer.topic}"},
            containerFactory = "listenerContainerFactory")
    @Override
    public void consumer(Event<?> event) {
        log.info("Executing listener...");
        if (event.getClass().isAssignableFrom(OrderEvent.class)) {
            OrderEvent orderEventCreated = (OrderEvent) event;
            log.info("Processing message.... id: {}",event.getId());
            Order order = ((OrderEvent) event).getData();

            inventoryPort.updateInventory(order);
        }
    }
}
