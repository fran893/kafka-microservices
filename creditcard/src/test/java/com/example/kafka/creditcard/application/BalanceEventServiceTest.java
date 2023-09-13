package com.example.kafka.creditcard.application;

import com.example.kafka.creditcard.infra.port.out.MessagingBroker;
import com.kafka.example.events.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}, bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class BalanceEventServiceTest {

    @Mock
    private BalanceService balanceService;
    @Autowired
    private MessagingBroker<ProducerFactory<String, Event<?>>, KafkaTemplate<String, Event<?>>> kafkaBroker;
    private BalanceEventService balanceEventService;
    private final String topic = "test-topic";


    @BeforeEach
    public void setUp() {
        balanceEventService = new BalanceEventService(balanceService, kafkaBroker);

        ReflectionTestUtils.setField(balanceEventService, "orderTopic", topic);
    }

    @Test
    public void consumerTest() throws InterruptedException {
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

        balanceEventService.consumer(orderEvent);


    }

    private CreditCardBalance mockBalance() {
        CreditCardBalance creditCardBalance = new CreditCardBalance();
        creditCardBalance.setCustomerId(1);
        creditCardBalance.setId(1L);
        creditCardBalance.setAmount(50.0);

        return creditCardBalance;
    }

}