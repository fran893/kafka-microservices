package com.example.kafka.creditcard.infra.port.in;

public interface MessagingEvent<T> {

    void consumer(T t);

}
