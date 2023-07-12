package com.example.kafka.creditcard.application;

import com.example.kafka.creditcard.infra.port.in.MessagingEvent;
import com.example.kafka.creditcard.infra.port.out.CreditCardBalanceRepository;
import com.kafka.example.events.domain.CreditCardEvent;
import com.kafka.example.events.domain.Event;
import com.kafka.example.events.domain.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BalanceEventService implements MessagingEvent<Event<?>> {

    private BalanceService balanceService;

    @Autowired
    public BalanceEventService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic}",
            containerFactory = "listenerContainerFactory",
            groupId = "grupo1")
    @Override
    public void consumer(Event<?> event) {
        if (event.getClass().isAssignableFrom(OrderEvent.class)) {
            OrderEvent orderEvent = (OrderEvent) event;
            log.info("Received Customer created event .... with Id={}, data={}",
                    orderEvent.getId(),
                    orderEvent.getData().toString());

            balanceService.updateBalance(orderEvent.getData());
        }
    }
}
