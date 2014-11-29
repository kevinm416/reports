package com.kevinm416.report.common.cache;

import java.util.concurrent.Callable;

import org.skife.jdbi.v2.Handle;

public interface CallableFactory<T> {

    Callable<T> get(Handle h, long id);

}
