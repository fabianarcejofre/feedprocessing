package com.faj.feedprocessing.provider.alpha;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.faj.feedprocessing.model.Message;
import com.faj.feedprocessing.model.ProviderName;
import com.faj.feedprocessing.provider.AbstractProviderStrategy;
import com.faj.feedprocessing.provider.alpha.converter.AlphaBetSettlementConverter;
import com.faj.feedprocessing.provider.alpha.converter.AlphaOddsChangeConverter;
import com.faj.feedprocessing.provider.alpha.model.OddsUpdate;
import com.faj.feedprocessing.provider.alpha.model.ProviderAlphaMessage;
import com.faj.feedprocessing.provider.alpha.model.Settlement;
import org.springframework.stereotype.Component;

@Component
public class ProviderAlphaStrategy extends AbstractProviderStrategy<ProviderAlphaMessage> {
    private final AlphaOddsChangeConverter alphaOddsChangeConverter;
    private final AlphaBetSettlementConverter alphaBetSettlementConverter;

    public ProviderAlphaStrategy(AlphaOddsChangeConverter alphaOddsChangeConverter, AlphaBetSettlementConverter alphaBetSettlementConverter) {
        this.alphaOddsChangeConverter = alphaOddsChangeConverter;
        this.alphaBetSettlementConverter = alphaBetSettlementConverter;
    }

    @Override
    public ProviderName getProviderName() {
        return ProviderName.PROVIDER_ALPHA;
    }

    @Override
    protected String getMessageTypeFieldName() {
        return "msg_type";
    }

    public ProviderAlphaMessage getProviderMessage(JsonNode incomingMessage) throws JsonProcessingException {
        String messageType = getMessageType(incomingMessage);
        return switch (messageType) {
            case "odds_update" -> objectMapper.treeToValue(incomingMessage, OddsUpdate.class);
            case "settlement" -> objectMapper.treeToValue(incomingMessage, Settlement.class);
            default -> throw new IllegalArgumentException("Invalid message type: " + messageType);
        };
    }

    @Override
    protected Message convertToMessage(ProviderAlphaMessage providerAlphaMessage) {
        if (providerAlphaMessage instanceof OddsUpdate oddsUpdate) {
            return alphaOddsChangeConverter.convert(oddsUpdate);
        } else if (providerAlphaMessage instanceof Settlement settlement) {
            return alphaBetSettlementConverter.convert(settlement);
        }
        throw new IllegalArgumentException("Unable to convert message due to unknown message type");
    }
}
