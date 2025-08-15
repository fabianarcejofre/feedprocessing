package com.faj.feedprocessing.provider.alpha.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OddsValues {
    private BigDecimal home;
    private BigDecimal draw;
    private BigDecimal away;

    @JsonProperty("1")
    public void setHome(BigDecimal home) {
        this.home = home;
    }

    public BigDecimal getHome() {
        return home;
    }

    @JsonProperty("X")
    public void setDraw(BigDecimal draw) {
        this.draw = draw;
    }

    public BigDecimal getDraw() {
        return draw;
    }

    @JsonProperty("2")
    public void setAway(BigDecimal away) {
        this.away = away;
    }

    public BigDecimal getAway() {
        return away;
    }
}
