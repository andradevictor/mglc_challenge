package com.magalu.cloud.api.application.DTO;

import java.time.LocalDate;

public record SummarizedDTO(String productSku, Number summarizedAmount, String useUnity, LocalDate summarizedDate) {
}
