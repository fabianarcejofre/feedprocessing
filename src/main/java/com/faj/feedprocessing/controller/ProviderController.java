package com.faj.feedprocessing.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.faj.feedprocessing.model.ProviderMessage;
import com.faj.feedprocessing.service.ProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping(value = "/{providerName}/feed", consumes = "application/json")
    public ResponseEntity<String> processFeed(@PathVariable String providerName, @RequestBody JsonNode incomingMessage) {
        ProviderMessage providerMessage;
        try {
            providerMessage = providerService.processMessage(providerName, incomingMessage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        }
        return ResponseEntity.ok("Message successfully received: " + providerMessage.message().getClass().getSimpleName());
    }
}
