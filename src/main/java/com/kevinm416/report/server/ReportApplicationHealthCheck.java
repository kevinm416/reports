package com.kevinm416.report.server;

import com.codahale.metrics.health.HealthCheck;

public class ReportApplicationHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }

}
