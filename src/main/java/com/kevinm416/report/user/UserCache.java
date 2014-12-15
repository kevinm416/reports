package com.kevinm416.report.user;

import java.util.concurrent.Callable;

import org.skife.jdbi.v2.Handle;

import com.kevinm416.report.common.cache.CallableFactory;
import com.kevinm416.report.common.cache.IdCache;

public class UserCache {

    private UserCache() {
        // util
    }

    public static IdCache<User> create() {
        return new IdCache<User>(new CallableFactory<User>() {
            @Override
            public Callable<User> get(Handle h, long id) {
                return getCacheMissCallable(h, id);
            }
        });
    }

    private static Callable<User> getCacheMissCallable(
            final Handle h,
            final long id) {
        return new Callable<User>() {
            @Override
            public User call() throws Exception {
                UserDAO residentCoordinatorDao = h.attach(UserDAO.class);
                return residentCoordinatorDao.loadUserById(id);
            }
        };
    }

}
