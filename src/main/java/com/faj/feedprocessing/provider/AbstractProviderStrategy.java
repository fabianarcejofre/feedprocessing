package com.faj.feedprocessing.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.faj.feedprocessing.model.Message;
import com.faj.feedprocessing.model.ProviderMessage;
import com.faj.feedprocessing.model.ProviderName;

public abstract class AbstractProviderStrategy<T> implements ProviderStrategy {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    public abstract ProviderName getProviderName();

    protected abstract String getMessageTypeFieldName();

    protected abstract T getProviderMessage(JsonNode incomingMessage) throws JsonProcessingException;

    protected abstract Message convertToMessage(T providerMessage);

    @Override
    public ProviderMessage processMessage(JsonNode incomingMessage) {
        if (incomingMessage == null || incomingMessage.isEmpty()) {
            throw new IllegalArgumentException("Incoming message must not be null or empty");
        }
        return new ProviderMessage(getProviderName(), parseIncomingMessage(incomingMessage));
    }

    protected String getMessageType(JsonNode incomingMessage) {
        JsonNode messageTypeNode = incomingMessage.findValue(getMessageTypeFieldName());
        if (messageTypeNode == null || messageTypeNode.isNull()) {
            throw new IllegalArgumentException("Message type field must not be null: " + getMessageTypeFieldName());
        }
        return messageTypeNode.asText();
    }

    protected Message parseIncomingMessage(JsonNode incomingMessage) {
        try {
            var providerMessage = getProviderMessage(incomingMessage);
            if (providerMessage == null) {
                throw new IllegalArgumentException("Message must not be null");
            }
            return convertToMessage(providerMessage);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error processing JSON message: " + e.getMessage(), e);
        }
    }
}
