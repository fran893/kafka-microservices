package com.example.kafka.orders.infra.port.out;

public interface MessagingBroker<P, T> {

    public P producerFactory();

    public T brokerTemplate();

}
