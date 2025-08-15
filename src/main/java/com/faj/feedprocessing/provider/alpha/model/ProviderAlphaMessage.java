package com.faj.feedprocessing.provider.alpha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ProviderAlphaMessage {
    protected String messageType;
    protected String eventId;

    @JsonProperty("msg_type")
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    @JsonProperty("event_id")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
}
