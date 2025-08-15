package com.faj.feedprocessing.model;

public enum ProviderName {
    PROVIDER_ALPHA("provider-alpha"), PROVIDER_BETA("provider-beta");

    private final String providerName;

    ProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderName() {
        return providerName;
    }

    public static ProviderName from(String providerName) {
        if (providerName == null || providerName.isEmpty()) {
            throw new IllegalArgumentException("Provider name must not be null or empty");
        }
        if (providerName.equalsIgnoreCase(PROVIDER_ALPHA.getProviderName())) {
            return PROVIDER_ALPHA;
        }
        if (providerName.equalsIgnoreCase(PROVIDER_BETA.getProviderName())) {
            return PROVIDER_BETA;
        }
        throw new IllegalArgumentException("Unknown provider name: " + providerName);
    }
}
