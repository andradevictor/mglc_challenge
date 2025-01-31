package com.magalu.cloud.in.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PulseEntity {

    @JsonProperty("tenant")
    String tenant;

    @JsonProperty("productSku")
    String productSku;

    @JsonProperty("useUnity")
    String useUnity;

    @JsonProperty("usedAmount")
    Double usedAmount;

    public PulseEntity() {
    }

    public PulseEntity(String tenant, String productSku, String useUnity, Double usedAmount) {
        this.tenant = tenant;
        this.productSku = productSku;
        this.usedAmount = usedAmount;
        this.useUnity = useUnity;
    }

    public String getTenant() {
        return tenant;
    }

    public String getProductSku() {
        return productSku;
    }

    public String getUseUnity() {
        return useUnity;
    }

    public Double getUsedAmount() {
        return usedAmount;
    }

}
