package com.faj.feedprocessing.provider.alpha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OddsUpdate extends ProviderAlphaMessage {
    private OddsValues oddsValues;

    @JsonProperty("values")
    public void setOddsValues(OddsValues oddsValues) {
        this.oddsValues = oddsValues;
    }

    public OddsValues getOddsValues() {
        return oddsValues;
    }
}
