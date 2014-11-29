package com.kevinm416.report.rc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.skife.jdbi.v2.Handle;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kevinm416.report.common.Throwables;

public class ResidentCoordinatorCache {

    private final Cache<Long, ResidentCoordinator> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build();

    public ResidentCoordinator loadResidentCoordinatorById(Handle h, long rcId) {
        try {
            return cache.get(rcId, getCacheMissCallable(h, rcId));
        } catch (ExecutionException e) {
            throw Throwables.propagate(e.getCause());
        }
    }

    private Callable<ResidentCoordinator> getCacheMissCallable(
            final Handle h,
            final long id) {
        return new Callable<ResidentCoordinator>() {
            @Override
            public ResidentCoordinator call() throws Exception {
                ResidentCoordinatorDAO residentCoordinatorDao = h.attach(ResidentCoordinatorDAO.class);
                return residentCoordinatorDao.loadResidentCoordinatorById(id);
            }
        };
    }

}
