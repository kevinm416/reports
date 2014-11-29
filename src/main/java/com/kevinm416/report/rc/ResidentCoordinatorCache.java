package com.kevinm416.report.rc;

import java.util.concurrent.Callable;

import org.skife.jdbi.v2.Handle;

import com.kevinm416.report.common.cache.CallableFactory;
import com.kevinm416.report.common.cache.IdCache;

public class ResidentCoordinatorCache {

    private ResidentCoordinatorCache() {
        // util
    }

    public static IdCache<ResidentCoordinator> create() {
        return new IdCache<ResidentCoordinator>(new CallableFactory<ResidentCoordinator>() {
            @Override
            public Callable<ResidentCoordinator> get(Handle h, long id) {
                return getCacheMissCallable(h, id);
            }
        });
    }

    private static Callable<ResidentCoordinator> getCacheMissCallable(
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
