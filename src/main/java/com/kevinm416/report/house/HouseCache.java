package com.kevinm416.report.house;

import java.util.concurrent.Callable;

import org.skife.jdbi.v2.Handle;

import com.kevinm416.report.common.cache.CallableFactory;
import com.kevinm416.report.common.cache.IdCache;

public class HouseCache {

    private HouseCache() {
        // util
    }

    public static IdCache<House> create() {
        return new IdCache<House>(new CallableFactory<House>() {
            @Override
            public Callable<House> get(Handle h, long id) {
                return getCacheMissCallable(h, id);
            }
        });
    }

    private static Callable<House> getCacheMissCallable(
            final Handle h,
            final long id) {
        return new Callable<House>() {
            @Override
            public House call() {
                HouseDAO houseDao = h.attach(HouseDAO.class);
                return houseDao.loadHouse(id);
            }
        };
    }

}
