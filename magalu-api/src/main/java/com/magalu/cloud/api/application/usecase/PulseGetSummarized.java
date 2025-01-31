package com.magalu.cloud.api.application.usecase;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.magalu.cloud.api.domain.model.Summarized;
import com.magalu.cloud.api.infrastructure.repository.SummarizedRepository;

@Component
public class PulseGetSummarized {

    @Autowired
    private SummarizedRepository pulseRepository;

    public PulseGetSummarized(){}

    public List<Summarized> loadSummarized(String tenant, LocalDate date) {
        return this.pulseRepository.findByTenantAndSummarizedDate(tenant, date);
    }
    
}
