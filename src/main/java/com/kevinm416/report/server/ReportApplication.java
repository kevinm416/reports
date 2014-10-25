package com.kevinm416.report.server;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.skife.jdbi.v2.DBI;

import com.kevinm416.report.resident.ResidentDAO;
import com.kevinm416.report.resident.ResidentResource;
import com.kevinm416.report.server.config.ReportServiceConfiguration;


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
        bootstrap.addBundle(new AssetsBundle("/assets", ""));
    }

    @Override
    public void run(ReportServiceConfiguration configuration,
            Environment environment) throws Exception {
        setupAuth(environment);

        environment.jersey().setUrlPattern("/api/*");

        DBIFactory dbiFactory = new DBIFactory();
        DBI jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "postgres");
        ResidentDAO residentDAO = jdbi.onDemand(ResidentDAO.class);

        ResidentResource residentResource = new ResidentResource(residentDAO);
        environment.jersey().register(residentResource);

        environment.healthChecks().register("test", new ReportApplicationHealthCheck());
    }

    private void setupAuth(Environment environment) {
        environment.jersey().register(new BasicAuthProvider<User>(
                new ReportApplicationAuthenticator(),
                "idk what this does"));
    }

    public static void main(String args[]) throws Exception {
        new ReportApplication().run(args);
    }

}
