package com.faj.feedprocessing.provider.alpha.converter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.faj.feedprocessing.model.BetResult;
import com.faj.feedprocessing.model.OddsChange;
import com.faj.feedprocessing.provider.alpha.model.OddsUpdate;
import org.springframework.stereotype.Component;

@Component
public class AlphaOddsChangeConverter {
    public OddsChange convert(OddsUpdate alphaOddsUpdate) {
        if (alphaOddsUpdate == null) {
            throw new IllegalArgumentException("OddsUpdate cannot be null");
        }
        if (alphaOddsUpdate.getEventId() == null || alphaOddsUpdate.getEventId().isEmpty()) {
            throw new IllegalArgumentException("Event ID must not be null or empty");
        }
        if (alphaOddsUpdate.getOddsValues() == null) {
            throw new IllegalArgumentException("Values must not be null");
        }

        Map<BetResult, BigDecimal> odds = new HashMap<BetResult, BigDecimal>();
        BigDecimal homeValue = alphaOddsUpdate.getOddsValues().getHome();
        if (homeValue == null) {
            throw new IllegalArgumentException("Value for Home must not be null");
        }
        BigDecimal drawValue = alphaOddsUpdate.getOddsValues().getDraw();
        if (drawValue == null) {
            throw new IllegalArgumentException("Value for Draw must not be null");
        }
        BigDecimal awayValue = alphaOddsUpdate.getOddsValues().getAway();
        if (awayValue == null) {
            throw new IllegalArgumentException("Value for Away must not be null");
        }

        odds.put(BetResult.HOME, homeValue);
        odds.put(BetResult.DRAW, drawValue);
        odds.put(BetResult.AWAY, awayValue);

        return new OddsChange(alphaOddsUpdate.getEventId(), odds);
    }
}
