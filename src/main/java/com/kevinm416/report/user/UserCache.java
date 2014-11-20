package com.kevinm416.report.user;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public enum UserCache {

    INSTANCE;

    private final Cache<UUID, User> userCache = CacheBuilder.newBuilder()
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();

    public Optional<User> getUser(UUID sessionToken) {
        return Optional.fromNullable(userCache.getIfPresent(sessionToken));
    }

    public void putUser(UUID sessionToken, User user) {
        userCache.put(sessionToken, user);
    }

    public void remove(UUID sessionToken) {
        userCache.invalidate(sessionToken);
    }

}
