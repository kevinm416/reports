package com.kevinm416.report.server;
import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.skife.jdbi.v2.DBI;

import com.kevinm416.report.server.config.ReportServiceConfiguration;
import com.kevinm416.report.test.TestResouce;


public class ReportApplication extends Application<ReportServiceConfiguration> {

    @Override
    public String getName() {
        return "report-application";
    }


    @Override
    public void initialize(Bootstrap<ReportServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<ReportServiceConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ReportServiceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(ReportServiceConfiguration configuration,
            Environment environment) throws Exception {

        TestResouce testResouce = new TestResouce();
        environment.jersey().register(testResouce);

        environment.jersey().register(new BasicAuthProvider<User>(
                new ReportApplicationAuthenticator(),
                "idk what this does"));

        DBIFactory dbiFactory = new DBIFactory();
        DBI jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "postgres");

        environment.healthChecks().register("test", new ReportApplicationHealthCheck());
    }

    public static void main(String args[]) throws Exception {
        new ReportApplication().run(args);
    }

}
