package com.faj.feedprocessing.provider.beta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ProviderBetaMessage {
    private String type;
    private String eventId;

    @JsonProperty("type")
    protected void setType(String type) {
        this.type = type;
    }

    protected String getType() {
        return type;
    }

    @JsonProperty("event_id")
    protected void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
}
