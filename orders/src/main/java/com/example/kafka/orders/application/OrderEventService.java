package com.example.kafka.orders.application;

import com.example.kafka.orders.infra.port.out.MessagingBroker;
import com.example.kafka.orders.infra.port.out.MessagingEvent;
import com.kafka.example.events.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class OrderEventService implements MessagingEvent<Order> {

    private MessagingBroker<ProducerFactory<String, Event<?>>, KafkaTemplate<String, Event<?>>> kafkaBroker;
    @Value("${app.kafka.producer.topic}")
    private String orderTopic;

    @Autowired
    public OrderEventService(MessagingBroker<ProducerFactory<String, Event<?>>, KafkaTemplate<String, Event<?>>> kafkaBroker) {
        this.kafkaBroker = kafkaBroker;
    }

    @Override
    public void publish(Order order) {
        OrderEvent orderEvent = new OrderEvent();

        orderEvent.setData(order);
        orderEvent.setId(UUID.randomUUID().toString());
        orderEvent.setType(EventType.CREATED);
        orderEvent.setDate(LocalDate.now());

        log.info("Sending message to kafka.., topic: {}", orderTopic);
        kafkaBroker.brokerTemplate().send(orderTopic, orderEvent);

    }
}
