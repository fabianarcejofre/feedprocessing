package com.faj.feedprocessing.provider;

import java.math.BigDecimal;

import com.faj.feedprocessing.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.faj.feedprocessing.provider.alpha.ProviderAlphaStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProviderAlphaStrategyTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProviderAlphaStrategy providerAlphaStrategy;

    @Test
    public void testProcessAlphaOddsUpdate_ReturnsOddsChange() throws JsonProcessingException {
        String alphaOddsUpdate = "{\n" +
                "\"msg_type\": \"odds_update\",\n" +
                "\"event_id\": \"ev123\",\n" +
                "\"values\": {\n" +
                "\"1\": 2.0,\n" +
                "\"X\": 3.1,\n" +
                "\"2\": 3.8\n" +
                "}\n" +
                "}";
        JsonNode jsonNode = objectMapper.readTree(alphaOddsUpdate);

        ProviderMessage providerMessage = providerAlphaStrategy.processMessage(jsonNode);

        assertEquals(ProviderName.PROVIDER_ALPHA, providerMessage.providerName());
        assertNotNull(providerMessage.message());
        assertInstanceOf(OddsChange.class, providerMessage.message());
        OddsChange oddsChange = (OddsChange) providerMessage.message();
        assertEquals("ev123", oddsChange.eventId());
        assertNotNull(oddsChange.odds());
        assertFalse(oddsChange.odds().isEmpty());
        assertEquals(0, oddsChange.odds().get(BetResult.HOME).compareTo(new BigDecimal("2.0")));
        assertEquals(0, oddsChange.odds().get(BetResult.DRAW).compareTo(new BigDecimal("3.1")));
        assertEquals(0, oddsChange.odds().get(BetResult.AWAY).compareTo(new BigDecimal("3.8")));
    }

    @Test
    public void testProcessAlphaSettlement_ReturnsOddsChange() throws JsonProcessingException {
        String alphaSettlement = "{\n" +
                "\"msg_type\": \"settlement\",\n" +
                "\"event_id\": \"ev123\",\n" +
                "\"outcome\": \"1\"\n" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(alphaSettlement);

        ProviderMessage providerMessage = providerAlphaStrategy.processMessage(jsonNode);

        assertEquals(ProviderName.PROVIDER_ALPHA, providerMessage.providerName());
        assertNotNull(providerMessage.message());
        assertInstanceOf(BetSettlement.class, providerMessage.message());
        BetSettlement betSettlement = (BetSettlement) providerMessage.message();
        assertEquals("ev123", betSettlement.eventId());
        assertEquals(BetResult.HOME, betSettlement.result());
    }

    @Test
    public void testProcessInvalidAlphaSettlement_ThrowsException() throws JsonProcessingException {
        String alphaSettlement = "{\n" +
                "\"msg_type\": \"unknown\"}";

        JsonNode jsonNode = objectMapper.readTree(alphaSettlement);

        assertThrows(IllegalArgumentException.class, () -> providerAlphaStrategy.processMessage(jsonNode));
    }
}
