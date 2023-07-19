package com.example.kafka.inventory.infra.port.in;

public interface MessagingEvent<T, Z> {

    void consumer(T t);

    void publish(Z z);

}
