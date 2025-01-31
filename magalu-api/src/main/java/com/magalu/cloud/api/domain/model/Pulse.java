package com.magalu.cloud.api.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Pulse(

    @NotBlank(message = "The field 'useUnity' is mandatory")
    String tenant,

    @NotBlank(message = "The field 'productSku' is mandatory")
    String productSku, 

    @Positive(message = "The field 'message' must be numeric and positive")
    Number usedAmount,

    @NotNull(message = "The field 'useUnity' is mandatory")
    UnitEnum useUnity
) {
}
