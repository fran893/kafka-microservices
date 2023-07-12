package com.kafka.example.events.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditCardEvent extends Event<CreditCardBalance> {
}
