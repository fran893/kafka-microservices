package com.example.kafka.inventory.infra.port.in;

public interface MessagingEvent<T> {

    void consumer(T t);

}
