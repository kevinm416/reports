package com.kevinm416.report.openid;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public enum OpenIdCache {

    INSTANCE;

    private final Cache<UUID, OpenIdState> consumerManagerCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(2, TimeUnit.MINUTES)
            .build();

    public Optional<OpenIdState> getOpenIdState(UUID sessionToken) {
        return Optional.fromNullable(consumerManagerCache.getIfPresent(sessionToken));
    }

    public void putOpenIdState(UUID sessionToken, OpenIdState openIdState) {
        consumerManagerCache.put(sessionToken, openIdState);
    }

    public void invalidate(UUID sessionToken) {
        consumerManagerCache.invalidate(sessionToken);
    }

}
