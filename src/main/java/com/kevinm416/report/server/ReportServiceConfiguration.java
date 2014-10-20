package com.kevinm416.report.server;
import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportServiceConfiguration extends Configuration {

    private final String test;

    @JsonCreator
    public ReportServiceConfiguration(@JsonProperty("test") String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

}
