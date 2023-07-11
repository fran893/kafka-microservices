package com.kafka.example.events.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class Event <T> {

    private String id;
    private LocalDate date;
    private EventType type;
    private T data;

}
