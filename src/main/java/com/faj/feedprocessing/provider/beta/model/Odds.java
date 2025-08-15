package com.faj.feedprocessing.provider.beta.model;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Odds extends ProviderBetaMessage {
    private Map<String, BigDecimal> values;

    @JsonProperty("odds")
    public void setValues(Map<String, BigDecimal> values) {
        this.values = values;
    }

    public Map<String, BigDecimal> getValues() {
        return values;
    }
}
