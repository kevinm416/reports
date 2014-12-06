package com.kevinm416.report.server;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.session.SessionHandler;
import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.kevinm416.report.auth.AuthInjectableProvider;
import com.kevinm416.report.auth.ReportApplicationRealm;
import com.kevinm416.report.auth.ReportApplicationShiroFilter;
import com.kevinm416.report.common.cache.IdCache;
import com.kevinm416.report.house.House;
import com.kevinm416.report.house.HouseCache;
import com.kevinm416.report.house.HouseDAO;
import com.kevinm416.report.house.HouseResource;
import com.kevinm416.report.resident.Resident;
import com.kevinm416.report.resident.ResidentCache;
import com.kevinm416.report.resident.ResidentDAO;
import com.kevinm416.report.resident.ResidentResource;
import com.kevinm416.report.server.config.ReportServiceConfiguration;
import com.kevinm416.report.shiftreport.ShiftReportResource;
import com.kevinm416.report.user.ResidentCoordinatorResource;
import com.kevinm416.report.user.User;
import com.kevinm416.report.user.UserCache;
import com.kevinm416.report.user.UserDAO;


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

        IdCache<User> residentCoordinatorCache = UserCache.create();
        IdCache<House> houseCache = HouseCache.create();
        IdCache<Resident> residentCache = ResidentCache.create();

        DBIFactory dbiFactory = new DBIFactory();
        DBI jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "postgres");
        ResidentDAO residentDAO = jdbi.onDemand(ResidentDAO.class);
        HouseDAO houseDAO = jdbi.onDemand(HouseDAO.class);
        UserDAO residentCoordinatorDao = jdbi.onDemand(UserDAO.class);

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

    private void setupAuth(Environment environment, UserDAO residentCoordinatorDAO) {
//        environment.jersey().register(new BasicAuthProvider<ResidentCoordinator>(
//                new ReportApplicationAuthenticator(residentCoordinatorDAO),
//                "reports"));

//        SecurityManager securityManager = ReportApplicationSecurity.createSecurityManager(residentCoordinatorDAO);
//        SecurityUtils.setSecurityManager(securityManager);

        environment.jersey().register(new AuthInjectableProvider());

        environment.servlets().setSessionHandler(new SessionHandler());
//        environment.servlets().addServletListeners(new EnvironmentLoaderListener());

        ReportApplicationRealm realm = ReportApplicationRealm.create(residentCoordinatorDAO);
        environment.servlets().addFilter("shiro-filter", new ReportApplicationShiroFilter(realm))
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*");

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
