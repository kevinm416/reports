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

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.kevinm416.report.common.cache.IdCache;
import com.kevinm416.report.house.House;
import com.kevinm416.report.house.HouseCache;
import com.kevinm416.report.house.HouseDAO;
import com.kevinm416.report.house.HouseResource;
import com.kevinm416.report.rc.ResidentCoordinator;
import com.kevinm416.report.rc.ResidentCoordinatorCache;
import com.kevinm416.report.rc.ResidentCoordinatorDAO;
import com.kevinm416.report.rc.ResidentCoordinatorResource;
import com.kevinm416.report.resident.Resident;
import com.kevinm416.report.resident.ResidentCache;
import com.kevinm416.report.resident.ResidentDAO;
import com.kevinm416.report.resident.ResidentResource;
import com.kevinm416.report.server.config.ReportServiceConfiguration;
import com.kevinm416.report.shiftreport.ShiftReportResource;


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
        configureObjectMapper(environment);

        environment.jersey().setUrlPattern("/api/*");

        IdCache<ResidentCoordinator> residentCoordinatorCache = ResidentCoordinatorCache.create();
        IdCache<House> houseCache = HouseCache.create();
        IdCache<Resident> residentCache = ResidentCache.create();

        DBIFactory dbiFactory = new DBIFactory();
        DBI jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "postgres");
        ResidentDAO residentDAO = jdbi.onDemand(ResidentDAO.class);
        HouseDAO houseDAO = jdbi.onDemand(HouseDAO.class);
        ResidentCoordinatorDAO residentCoordinatorDao = jdbi.onDemand(ResidentCoordinatorDAO.class);

        setupAuth(environment, residentCoordinatorDao);

        ResidentResource residentResource = new ResidentResource(residentDAO, residentCache);
        environment.jersey().register(residentResource);

        ResidentCoordinatorResource residentCoordinatorResource = new ResidentCoordinatorResource(residentCoordinatorDao);
        environment.jersey().register(residentCoordinatorResource);

        HouseResource houseResource = new HouseResource(houseDAO);
        environment.jersey().register(houseResource);

        ShiftReportResource shiftReportResource = new ShiftReportResource(jdbi, houseCache, residentCoordinatorCache, residentCache);
        environment.jersey().register(shiftReportResource);

        environment.healthChecks().register("test", new ReportApplicationHealthCheck());
    }

    private void setupAuth(Environment environment, ResidentCoordinatorDAO residentCoordinatorDAO) {
        environment.jersey().register(new BasicAuthProvider<ResidentCoordinator>(
                new ReportApplicationAuthenticator(residentCoordinatorDAO),
                "reports"));
    }

    private void configureObjectMapper(Environment environment) {
        environment.getObjectMapper().configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
    }

    public static void main(String args[]) throws Exception {
        new ReportApplication().run(args);
    }

}
