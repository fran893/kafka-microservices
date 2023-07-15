package com.example.kafka.creditcard.infra.port.out;

public interface MessagingBroker<P, T> {

    public P producerFactory();

    public T brokerTemplate();

}
