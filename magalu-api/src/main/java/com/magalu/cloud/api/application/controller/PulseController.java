package com.magalu.cloud.api.application.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.magalu.cloud.api.application.DTO.PulseDTO;
import com.magalu.cloud.api.application.DTO.SummarizedDTO;
import com.magalu.cloud.api.application.mapper.PulseMapper;
import com.magalu.cloud.api.application.usecase.PulseGetSummarized;
import com.magalu.cloud.api.application.usecase.PulseProducerUseCase;
import com.magalu.cloud.api.domain.model.Pulse;

import jakarta.validation.Valid;

@Controller
public class PulseController {

    @Autowired
    private PulseProducerUseCase pulseProducerUseCase;

    @Autowired
    private PulseGetSummarized pulseSummarizedUseCase;

    @PostMapping("/api/v1/pulse")
    public ResponseEntity<PulseDTO> sendPulse(@Valid @RequestBody Pulse pulse) throws JsonProcessingException {
        pulseProducerUseCase.processPulse(pulse);
        return ResponseEntity.ok(PulseMapper.INSTANCE.toPulseDTO(pulse));
    }

    @GetMapping("/api/v1/pulse/{tenant}/{date}")
    public ResponseEntity<List<SummarizedDTO>> getSummarizedPulse(@PathVariable String tenant, @PathVariable LocalDate date) throws JsonProcessingException {
        var summarized = pulseSummarizedUseCase.loadSummarized(tenant, date);
        return ResponseEntity.ok(PulseMapper.INSTANCE.toSummarizedDTO(summarized));
    }
}