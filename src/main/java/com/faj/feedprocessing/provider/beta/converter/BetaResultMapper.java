package com.faj.feedprocessing.provider.beta.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.faj.feedprocessing.model.BetResult;
import org.springframework.stereotype.Component;

@Component
public class BetaResultMapper {
    private final Map<String, BetResult> resultMap = new HashMap<>(3);

    public BetaResultMapper() {
        resultMap.put("home", BetResult.HOME);
        resultMap.put("draw", BetResult.DRAW);
        resultMap.put("away", BetResult.AWAY);
    }

    public Set<String> validKeys() {
        return resultMap.keySet();
    }

    public BetResult get(String betaResult) {
        return resultMap.get(betaResult);
    }
}
