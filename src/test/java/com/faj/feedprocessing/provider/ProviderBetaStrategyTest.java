package com.faj.feedprocessing.provider;

import java.math.BigDecimal;

import com.faj.feedprocessing.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.faj.feedprocessing.provider.beta.ProviderBetaStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProviderBetaStrategyTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProviderBetaStrategy providerBetaStrategy;

    @Test
    public void testProcessBetaOdds_ReturnsOddsChange() throws JsonProcessingException {
        String betaOdds = "{\n" +
                "\"type\": \"ODDS\",\n" +
                "\"event_id\": \"ev456\",\n" +
                "\"odds\": {\n" +
                "\"home\": 1.95,\n" +
                "\"draw\": 3.2,\n" +
                "\"away\": 4.0\n" +
                "}\n" +
                "}";
        JsonNode jsonNode = objectMapper.readTree(betaOdds);

        ProviderMessage providerMessage = providerBetaStrategy.processMessage(jsonNode);

        assertEquals(ProviderName.PROVIDER_BETA, providerMessage.providerName());
        assertNotNull(providerMessage.message());
        assertInstanceOf(OddsChange.class, providerMessage.message());
        OddsChange oddsChange = (OddsChange) providerMessage.message();
        assertEquals("ev456", oddsChange.eventId());
        assertNotNull(oddsChange.odds());
        assertFalse(oddsChange.odds().isEmpty());
        assertEquals(0, oddsChange.odds().get(BetResult.HOME).compareTo(new BigDecimal("1.95")));
        assertEquals(0, oddsChange.odds().get(BetResult.DRAW).compareTo(new BigDecimal("3.2")));
        assertEquals(0, oddsChange.odds().get(BetResult.AWAY).compareTo(new BigDecimal("4.0")));
    }

    @Test
    public void testProcessBetaSettlement_ReturnsOddsChange() throws JsonProcessingException {
        String betaSettlement = "{\n" +
                "\"type\": \"SETTLEMENT\",\n" +
                "\"event_id\": \"ev456\",\n" +
                "\"result\": \"away\"\n" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(betaSettlement);

        ProviderMessage providerMessage = providerBetaStrategy.processMessage(jsonNode);

        assertEquals(ProviderName.PROVIDER_BETA, providerMessage.providerName());
        assertNotNull(providerMessage.message());
        assertInstanceOf(BetSettlement.class, providerMessage.message());
        BetSettlement betSettlement = (BetSettlement) providerMessage.message();
        assertEquals("ev456", betSettlement.eventId());
        assertEquals(BetResult.AWAY, betSettlement.result());
    }

    @Test
    public void testProcessInvalidBetaMessage_ThrowsException() throws JsonProcessingException {
        String betaMessage = "{\n" +
                "\"type\": \"unknown\"}";

        JsonNode jsonNode = objectMapper.readTree(betaMessage);

        assertThrows(IllegalArgumentException.class, () -> providerBetaStrategy.processMessage(jsonNode));
    }
}
