package com.faj.feedprocessing.provider.alpha.converter;

import java.util.HashMap;
import java.util.Map;

import com.faj.feedprocessing.model.BetResult;
import com.faj.feedprocessing.model.BetSettlement;
import com.faj.feedprocessing.provider.alpha.model.Settlement;
import org.springframework.stereotype.Component;

@Component
public class AlphaBetSettlementConverter {
    private final Map<String, BetResult> resultMap = new HashMap<>(3);

    public AlphaBetSettlementConverter() {
        resultMap.put("1", BetResult.HOME);
        resultMap.put("X", BetResult.DRAW);
        resultMap.put("2", BetResult.AWAY);
    }

    public BetSettlement convert(Settlement alphaSettlement) {
        if (alphaSettlement == null) {
            throw new IllegalArgumentException("alphaSettlement cannot be null");
        }
        if (alphaSettlement.getEventId() == null || alphaSettlement.getEventId().isEmpty()) {
            throw new IllegalArgumentException("Event ID must not be null or empty");
        }
        if (alphaSettlement.getOutcome() == null || alphaSettlement.getOutcome().isEmpty()) {
            throw new IllegalArgumentException("Outcome must not be null or empty");
        }

        BetResult result = resultMap.get(alphaSettlement.getOutcome());
        if (result == null) {
            throw new IllegalArgumentException("result '" + alphaSettlement.getOutcome() + "' is unknown");
        }
        return new BetSettlement(alphaSettlement.getEventId(), result);
    }
}
