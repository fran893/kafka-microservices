package com.example.kafka.creditcard.application;

import com.example.kafka.creditcard.infra.port.in.MessagingEvent;
import com.example.kafka.creditcard.infra.port.out.CreditCardBalanceRepository;
import com.example.kafka.creditcard.infra.port.out.MessagingBroker;
import com.kafka.example.events.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class BalanceEventService implements MessagingEvent<Event<?>, OrderEvent> {

    private BalanceService balanceService;

    private MessagingBroker<ProducerFactory<String, Event<?>>, KafkaTemplate<String, Event<?>>> kafkaBroker;
    @Value("${spring.kafka.producer.topic}")
    private String orderTopic;

    @Autowired
    public BalanceEventService(BalanceService balanceService,
                               MessagingBroker<ProducerFactory<String, Event<?>>, KafkaTemplate<String, Event<?>>> kafkaBroker) {
        this.balanceService = balanceService;
        this.kafkaBroker = kafkaBroker;
    }

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic}",
            containerFactory = "listenerContainerFactory",
            groupId = "grupo1")
    @Override
    public void consumer(Event<?> event) {
        log.info("Executing consumer...");
        if (event.getClass().isAssignableFrom(OrderEvent.class)) {
            OrderEvent orderEvent = (OrderEvent) event;
            log.info("Processing payment of order:  {}, data: {}", orderEvent.getId(), orderEvent.getData().toString());

            CreditCardBalance balance = balanceService.getBalance(orderEvent.getData().getCustomerId());

            if((balance.getAmount() - orderEvent.getData().getPrice()) > 0) {
                balanceService.updateBalance(orderEvent.getData());
                orderEvent.setType(EventType.CONFIRMED);
                log.info("Payment confirmed, order confirmed");
            } else {
                orderEvent.setType(EventType.REJECTED);
                log.info("Payment rejected, order rejected");
            }

            this.publish(orderEvent);
        }
    }

    @Override
    public void publish(OrderEvent orderEvent) {
        orderEvent.setDate(LocalDate.now());
        orderEvent.setId(UUID.randomUUID().toString());

        log.info("Sending message to kafka topic: {}", orderEvent);
        kafkaBroker.brokerTemplate().send(orderTopic, orderEvent);
    }
}
