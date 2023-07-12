package com.example.kafka.creditcard.infra.port.in;

public interface BrokerMessagingConsumer<P, T> {

    P consumerFactory();

    T listenerContainerFactory();

}
