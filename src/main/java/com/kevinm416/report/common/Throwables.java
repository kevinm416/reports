package com.kevinm416.report.common;

public class Throwables {

    private Throwables() {
        // util
    }

    public static RuntimeException propagate(Throwable t) {
        if (t instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        throw com.google.common.base.Throwables.propagate(t);
    }

}
