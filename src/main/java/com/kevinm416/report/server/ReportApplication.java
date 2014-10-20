package com.kevinm416.report.server;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class ReportApplication extends Application<ReportServiceConfiguration> {

    @Override
    public String getName() {
        return "report-application";
    }


    @Override
    public void initialize(Bootstrap<ReportServiceConfiguration> bootstrap) {
        // nothing to do
    }

    @Override
    public void run(ReportServiceConfiguration configuration,
            Environment environment) throws Exception {
        String test = configuration.getTest();

        TestResouce testResouce = new TestResouce(test);

        environment.jersey().register(testResouce);
    }

    public static void main(String args[]) throws Exception {
        new ReportApplication().run(args);
    }

}
