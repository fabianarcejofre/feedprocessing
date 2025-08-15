package com.faj.feedprocessing.provider.beta.converter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.faj.feedprocessing.model.BetResult;
import com.faj.feedprocessing.model.OddsChange;
import com.faj.feedprocessing.provider.beta.model.Odds;
import org.springframework.stereotype.Component;

@Component
public class BetaOddsChangeConverter {
    private final BetaResultMapper betaResultMapper;

    public BetaOddsChangeConverter(BetaResultMapper betaResultMapper) {
        this.betaResultMapper = betaResultMapper;
    }

    public OddsChange convert(Odds betaOdds) {
        if (betaOdds == null) {
            throw new IllegalArgumentException("betaSettlement cannot be null");
        }
        if (betaOdds.getEventId() == null || betaOdds.getEventId().isEmpty()) {
            throw new IllegalArgumentException("Event ID must not be null or empty");
        }
        if (betaOdds.getValues() == null) {
            throw new IllegalArgumentException("Values must not be null");
        }

        Map<BetResult, BigDecimal> odds = new HashMap<BetResult, BigDecimal>();
        for (String key: betaResultMapper.validKeys()) {
            BigDecimal value = betaOdds.getValues().get(key);
            if (value == null) {
                throw new IllegalArgumentException("Value for '" + key + "' must not be null");
            }
            odds.put(betaResultMapper.get(key), value);
        }

        return new OddsChange(betaOdds.getEventId(), odds);
    }
}
