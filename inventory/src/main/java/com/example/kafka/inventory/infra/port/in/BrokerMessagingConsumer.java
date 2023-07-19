package com.example.kafka.inventory.infra.port.in;

public interface BrokerMessagingConsumer<P, T> {

    P consumerFactory();

    T listenerContainerFactory();

}
