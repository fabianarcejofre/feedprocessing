package com.faj.feedprocessing.queue;

import com.faj.feedprocessing.model.ProviderMessage;

public interface MessageQueue {
    void sendProviderMessage(ProviderMessage providerMessage);
}
