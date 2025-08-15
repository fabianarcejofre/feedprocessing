package com.faj.feedprocessing.model;

public record BetSettlement(String eventId, BetResult result) implements Message { }