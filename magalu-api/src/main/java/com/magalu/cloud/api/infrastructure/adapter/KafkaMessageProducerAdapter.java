package com.magalu.cloud.api.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.magalu.cloud.api.domain.port.KafkaMessageProducerPort;

@Component
public class KafkaMessageProducerAdapter implements KafkaMessageProducerPort {

    @Value("${magalu.kafka.producer.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMessageProducerAdapter(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void producerMessage(String pulse) {
        kafkaTemplate.send(topic, pulse);
    }

}