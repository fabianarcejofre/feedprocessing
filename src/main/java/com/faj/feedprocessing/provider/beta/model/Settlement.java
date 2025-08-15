package com.faj.feedprocessing.provider.beta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Settlement extends ProviderBetaMessage {
    private String result;

    @JsonProperty("result")
    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
