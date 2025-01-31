package com.magalu.cloud.api.application.usecase;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magalu.cloud.api.domain.model.Pulse;
import com.magalu.cloud.api.domain.port.KafkaMessageProducerPort;

@Component
public class PulseUseCase {

    private KafkaMessageProducerPort pulseMessagePort;

    public PulseUseCase(KafkaMessageProducerPort pulseMessagePort) {
        this.pulseMessagePort = pulseMessagePort;
    }

    public void processPulse(Pulse pulse) throws JsonProcessingException {
        pulseMessagePort.producerMessage(new ObjectMapper().writeValueAsString(pulse));
    }
}
