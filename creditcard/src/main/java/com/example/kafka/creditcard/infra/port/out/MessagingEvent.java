package com.example.kafka.creditcard.infra.port.out;

public interface MessagingEvent<T> {

    void publish(T t);

}
