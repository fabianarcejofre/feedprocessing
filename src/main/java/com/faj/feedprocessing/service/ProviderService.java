package com.faj.feedprocessing.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.faj.feedprocessing.model.ProviderMessage;
import com.faj.feedprocessing.model.ProviderName;
import com.faj.feedprocessing.queue.MessageQueue;
import com.faj.feedprocessing.provider.ProviderStrategy;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    private final Map<ProviderName, ProviderStrategy> providerStrategies;
    private final MessageQueue messageQueue;

    public ProviderService(List<ProviderStrategy> providerStrategies, MessageQueue messageQueue) {
        this.providerStrategies = new HashMap<>(providerStrategies.size());
        providerStrategies.forEach(strategy -> {
            this.providerStrategies.put(strategy.getProviderName(), strategy);
        });
        this.messageQueue = messageQueue;
    }

    public ProviderMessage processMessage(String providerName, JsonNode incomingMessage) {
        ProviderStrategy strategy = providerStrategies.get(ProviderName.from(providerName));
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported provider: " + providerName);
        }
        ProviderMessage providerMessage;
        try {
            providerMessage = strategy.processMessage(incomingMessage);
        } catch (Exception e) {
            throw new IllegalArgumentException("Message processing failed: " + e.getMessage());
        }
        messageQueue.sendProviderMessage(providerMessage);
        return providerMessage;
    }
}
