package com.kevinm416.report.server.config;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportServiceConfiguration extends Configuration {

    @Valid
    @NotNull
    private final DataSourceFactory dataSourceFactory;

    @JsonCreator
    public ReportServiceConfiguration(
            @JsonProperty("database") DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

}
