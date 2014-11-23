package com.kevinm416.report.server;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.kevinm416.report.house.HouseDAO;
import com.kevinm416.report.house.HouseResource;
import com.kevinm416.report.openid.OpenIdAuthProvider;
import com.kevinm416.report.openid.OpenIdResource;
import com.kevinm416.report.rc.ResidentCoordinatorDAO;
import com.kevinm416.report.rc.ResidentCoordinatorResource;
import com.kevinm416.report.resident.ResidentDAO;
import com.kevinm416.report.resident.ResidentResource;
import com.kevinm416.report.server.config.ReportServiceConfiguration;
import com.kevinm416.report.shiftreport.ShiftReportResource;
import com.kevinm416.report.user.UserSessionDAO;
import com.kevinm416.report.user.UserSessionResource;


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
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(ReportServiceConfiguration configuration,
            Environment environment) throws Exception {
        environment.jersey().setUrlPattern("/api/*");
        environment.getObjectMapper().configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);

        DBIFactory dbiFactory = new DBIFactory();
        DBI jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "postgres");
        ResidentDAO residentDAO = jdbi.onDemand(ResidentDAO.class);
        HouseDAO houseDAO = jdbi.onDemand(HouseDAO.class);
        ResidentCoordinatorDAO residentCoordinatorDao = jdbi.onDemand(ResidentCoordinatorDAO.class);
        UserSessionDAO userSessionDao = jdbi.onDemand(UserSessionDAO.class);

        OpenIdResource openIdResource = new OpenIdResource(jdbi, userSessionDao);
        environment.jersey().register(openIdResource);

        UserSessionResource userSessionResource = new UserSessionResource();
        environment.jersey().register(userSessionResource);

        ResidentResource residentResource = new ResidentResource(residentDAO);
        environment.jersey().register(residentResource);

        ResidentCoordinatorResource residentCoordinatorResource = new ResidentCoordinatorResource(residentCoordinatorDao);
        environment.jersey().register(residentCoordinatorResource);

        HouseResource houseResource = new HouseResource(houseDAO);
        environment.jersey().register(houseResource);

        ShiftReportResource shiftReportResource = new ShiftReportResource(jdbi);
        environment.jersey().register(shiftReportResource);

        setupAuth(environment, userSessionDao);

        environment.healthChecks().register("test", new ReportApplicationHealthCheck());
    }

    private void setupAuth(Environment environment, UserSessionDAO userSessionDAO) {
        environment.jersey().register(
                new OpenIdAuthProvider(new ReportApplicationAuthenticator(userSessionDAO)));
    }

    public static void main(String args[]) throws Exception {
        new ReportApplication().run(args);
    }

}
