package com.kevinm416.report.server;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import com.google.common.base.Optional;
import com.kevinm416.report.rc.ResidentCoordinator;
import com.kevinm416.report.rc.ResidentCoordinatorDAO;
import com.kevinm416.report.rc.api.CreateResidentCoordinatorForm;

public class ReportApplicationAuthenticator implements Authenticator<BasicCredentials, ResidentCoordinator> {

    private final ResidentCoordinatorDAO ResidentCoordinatorDAO;

    public ReportApplicationAuthenticator(
            ResidentCoordinatorDAO residentCoordinatorDAO) {
        ResidentCoordinatorDAO = residentCoordinatorDAO;
    }

    @Override
    public Optional<ResidentCoordinator> authenticate(BasicCredentials credentials)
            throws AuthenticationException {
        if ("password".equals(credentials.getPassword())) {
            ResidentCoordinator rc = ResidentCoordinatorDAO.loadResidentCoordinatorByName(credentials.getUsername());
            if (rc != null) {
                return Optional.of(rc);
            } else {
                long rcId = ResidentCoordinatorDAO.createResidentCoordinator(new CreateResidentCoordinatorForm(credentials.getUsername()));
                return Optional.of(new ResidentCoordinator(rcId, credentials.getUsername()));
            }
        } else {
            return Optional.absent();
        }
    }

}
