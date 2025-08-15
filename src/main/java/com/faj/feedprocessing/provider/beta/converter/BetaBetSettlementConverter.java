package com.faj.feedprocessing.provider.beta.converter;

import com.faj.feedprocessing.model.BetResult;
import com.faj.feedprocessing.model.BetSettlement;
import com.faj.feedprocessing.provider.beta.model.Settlement;
import org.springframework.stereotype.Component;

@Component
public class BetaBetSettlementConverter {
    private final BetaResultMapper betaResultMapper;

    public BetaBetSettlementConverter(BetaResultMapper betaResultMapper) {
        this.betaResultMapper = betaResultMapper;
    }

    public BetSettlement convert(Settlement betaSettlement) {
        if (betaSettlement == null) {
            throw new IllegalArgumentException("betaSettlement cannot be null");
        }
        if (betaSettlement.getEventId() == null || betaSettlement.getEventId().isEmpty()) {
            throw new IllegalArgumentException("Event ID must not be null or empty");
        }
        if (betaSettlement.getResult() == null || betaSettlement.getResult().isEmpty()) {
            throw new IllegalArgumentException("Outcome must not be null or empty");
        }

        BetResult result = betaResultMapper.get(betaSettlement.getResult());
        if (result == null) {
            throw new IllegalArgumentException("result '" + betaSettlement.getResult() + "' is unknown");
        }
        return new BetSettlement(betaSettlement.getEventId(), result);
    }
}
