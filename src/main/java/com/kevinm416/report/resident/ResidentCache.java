package com.kevinm416.report.resident;

import java.util.concurrent.Callable;

import org.skife.jdbi.v2.Handle;

import com.kevinm416.report.common.cache.CallableFactory;
import com.kevinm416.report.common.cache.IdCache;

public class ResidentCache {

    private ResidentCache() {
        // util
    }

    public static IdCache<Resident> create() {
        return new IdCache<Resident>(new CallableFactory<Resident>() {
            @Override
            public Callable<Resident> get(Handle h, long id) {
                return getCacheMissCallable(h, id);
            }
        });
    }

    private static Callable<Resident> getCacheMissCallable(
            final Handle h,
            final long id) {
        return new Callable<Resident>() {
            @Override
            public Resident call() throws Exception {
                ResidentDAO residentDao = h.attach(ResidentDAO.class);
                return residentDao.loadResidentIncludeDeleted(id);
            }
        };
    }

}
