package com.faj.feedprocessing.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.faj.feedprocessing.model.ProviderMessage;
import com.faj.feedprocessing.model.ProviderName;

public interface ProviderStrategy {
    ProviderName getProviderName();
    ProviderMessage processMessage(JsonNode incomingMessage);
}
