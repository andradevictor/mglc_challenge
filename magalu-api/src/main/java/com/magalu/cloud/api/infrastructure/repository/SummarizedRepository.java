package com.magalu.cloud.api.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.magalu.cloud.api.domain.model.Summarized;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SummarizedRepository extends JpaRepository<Summarized, Long> {
    List<Summarized> findByTenantAndSummarizedDate(String tenant, LocalDate date);
}
