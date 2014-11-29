package com.kevinm416.report.common.cache;

import java.util.concurrent.ExecutionException;

import org.skife.jdbi.v2.Handle;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kevinm416.report.common.Throwables;

public class IdCache<T> {

    private final CallableFactory<T> loadFn;
    private final Cache<Long, T> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .build();

    public IdCache(CallableFactory<T> loadFn) {
        this.loadFn = loadFn;
    }

    public T loadById(Handle h, long id) {
        try {
            return cache.get(id, loadFn.get(h, id));
        } catch (ExecutionException e) {
            throw Throwables.propagate(e.getCause());
        }
    }

    public void invalidate(long id) {
        cache.invalidate(id);
    }

}
