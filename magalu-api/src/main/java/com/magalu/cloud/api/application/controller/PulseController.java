package com.magalu.cloud.api.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magalu.cloud.api.application.usecase.PulseUseCase;
import com.magalu.cloud.api.domain.model.Pulse;

import jakarta.validation.Valid;

@Controller
public class PulseController {

    @Autowired
    private PulseUseCase pulseUseCase;

    @PostMapping("/api/v1/sendPulse")
    public String sendPulse(@Valid @RequestBody Pulse pulse) throws JsonProcessingException {
        pulseUseCase.processPulse(pulse);
        return "Mensagem enviada para o tópico: ";
    }
}