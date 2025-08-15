package com.faj.feedprocessing.model;

import java.math.BigDecimal;
import java.util.Map;

public record OddsChange(String eventId, Map<BetResult, BigDecimal> odds) implements Message { }