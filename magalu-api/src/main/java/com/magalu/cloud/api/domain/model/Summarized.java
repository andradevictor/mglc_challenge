package com.magalu.cloud.api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "summarized_pulse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Summarized {

    @Id
    private Long id;

    @Column(name = "tenant")
    private String tenant;

    @Column(name = "product_sku")
    private String productSku;

    @Column(name = "summarized_amount")
    private BigDecimal summarizedAmount;

    @Column(name = "use_unity")
    private String useUnity;

    @Column(name = "summarized_date")
    private LocalDate summarizedDate;
}
