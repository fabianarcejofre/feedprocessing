package com.faj.feedprocessing.provider.beta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.faj.feedprocessing.model.Message;
import com.faj.feedprocessing.model.ProviderName;
import com.faj.feedprocessing.provider.AbstractProviderStrategy;
import com.faj.feedprocessing.provider.beta.converter.BetaBetSettlementConverter;
import com.faj.feedprocessing.provider.beta.converter.BetaOddsChangeConverter;
import com.faj.feedprocessing.provider.beta.model.Odds;
import com.faj.feedprocessing.provider.beta.model.ProviderBetaMessage;
import com.faj.feedprocessing.provider.beta.model.Settlement;
import org.springframework.stereotype.Component;

@Component
public class ProviderBetaStrategy extends AbstractProviderStrategy<ProviderBetaMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BetaOddsChangeConverter betaOddsChangeConverter;
    private final BetaBetSettlementConverter betaBetSettlementConverter;

    public ProviderBetaStrategy(BetaOddsChangeConverter betaOddsChangeConverter, BetaBetSettlementConverter betaBetSettlementConverter) {
        this.betaOddsChangeConverter = betaOddsChangeConverter;
        this.betaBetSettlementConverter = betaBetSettlementConverter;
    }

    @Override
    public ProviderName getProviderName() {
        return ProviderName.PROVIDER_BETA;
    }

    @Override
    protected String getMessageTypeFieldName() {
        return "type";
    }

    @Override
    protected ProviderBetaMessage getProviderMessage(JsonNode incomingMessage) throws JsonProcessingException {
        String messageType = getMessageType(incomingMessage);
        return switch (messageType) {
            case "ODDS" -> objectMapper.treeToValue(incomingMessage, Odds.class);
            case "SETTLEMENT" -> objectMapper.treeToValue(incomingMessage, Settlement.class);
            default -> throw new IllegalArgumentException("Invalid message type: " + messageType);
        };
    }

    @Override
    protected Message convertToMessage(ProviderBetaMessage providerBetaMessage) {
        if (providerBetaMessage instanceof Odds odds) {
            return betaOddsChangeConverter.convert(odds);
        } else if (providerBetaMessage instanceof Settlement settlement) {
            return betaBetSettlementConverter.convert(settlement);
        }
        throw new IllegalArgumentException("Unable to convert message due to unknown message type");
    }
}
