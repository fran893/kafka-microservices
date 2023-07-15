package com.example.kafka.creditcard.infra.port.in;

public interface MessagingEvent<T, Z> {

    void consumer(T t);

    void publish(Z z);

}
