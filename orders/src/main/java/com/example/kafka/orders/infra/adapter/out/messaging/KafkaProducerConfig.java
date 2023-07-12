package com.example.kafka.orders.infra.adapter.out.messaging;

import com.example.kafka.orders.infra.port.out.MessagingBroker;
import com.kafka.example.events.domain.Event;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig implements MessagingBroker<ProducerFactory<String, Event<?>>, KafkaTemplate<String, Event<?>>> {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializationClassConfig;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Override
    public ProducerFactory<String, Event<?>> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                keySerializationClassConfig);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                valueSerializer);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Override
    public KafkaTemplate<String, Event<?>> brokerTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
