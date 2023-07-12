package com.example.kafka.orders.infra.port.out;

public interface MessagingEvent<T> {

    void publish(T t);

}
