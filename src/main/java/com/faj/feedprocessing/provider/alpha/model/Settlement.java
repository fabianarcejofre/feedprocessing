package com.faj.feedprocessing.provider.alpha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Settlement extends ProviderAlphaMessage {
    private String outcome;

    @JsonProperty("outcome")
    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getOutcome() {
        return outcome;
    }
}
