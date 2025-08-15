package com.faj.feedprocessing.queue;

import com.faj.feedprocessing.model.ProviderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InMemoryMessageQueue implements MessageQueue {
    private final Logger logger = LoggerFactory.getLogger(InMemoryMessageQueue.class);

    @Override
    public void sendProviderMessage(ProviderMessage providerMessage) {
        logger.info("Sending provider message to queue: {}", providerMessage);
    }
}
