package com.magalu.cloud.api.domain.port;

public interface KafkaMessageProducerPort {
    void producerMessage(String pulse);
}
