package com.example.kafka.creditcard.application;

import com.example.kafka.creditcard.infra.port.out.MessagingBroker;
import com.kafka.example.events.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

@SpringBootTest
class BalanceEventServiceTest {

    @Mock
    private BalanceService balanceService;
    @Mock
    private MessagingBroker<ProducerFactory<String, Event<?>>, KafkaTemplate<String, Event<?>>> kafkaBroker;
    private BalanceEventService balanceEventService;
    private final String topic = "test-topic";


    @BeforeEach
    public void setUp() {
        balanceEventService = new BalanceEventService(balanceService, kafkaBroker);

        ReflectionTestUtils.setField(balanceEventService, "orderTopic", topic);
    }

    @Test
    public void consumerOrderConfirmedTest() {
        Order order = new Order();
        order.setProductId(1);
        order.setPrice(25.0);
        order.setCustomerId(1);
        order.setOrderId(1);

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setId("123");
        orderEvent.setDate(LocalDate.now());
        orderEvent.setData(order);
        orderEvent.setType(EventType.CREATED);

        Mockito.when(balanceService.getBalance(Mockito.anyInt())).thenReturn(mockBalance());

        KafkaTemplate<String, Event<?>> kafkaTemplate = Mockito.mock(KafkaTemplate.class);

        Mockito.when(kafkaBroker.brokerTemplate()).thenReturn(kafkaTemplate);

        balanceEventService.publish(orderEvent);

        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(Mockito.eq(topic), Mockito.any());
    }

    private CreditCardBalance mockBalance() {
        CreditCardBalance creditCardBalance = new CreditCardBalance();
        creditCardBalance.setCustomerId(1);
        creditCardBalance.setId(1L);
        creditCardBalance.setAmount(50.0);

        return creditCardBalance;
    }

}