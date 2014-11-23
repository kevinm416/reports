package com.kevinm416.report.server;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.UUID;

import com.google.common.base.Optional;
import com.kevinm416.report.openid.OpenIdCredentials;
import com.kevinm416.report.user.UserSession;
import com.kevinm416.report.user.UserSessionDAO;

public class ReportApplicationAuthenticator implements Authenticator<OpenIdCredentials, UserSession> {

    private final UserSessionDAO userSessionDao;

    public ReportApplicationAuthenticator(UserSessionDAO userSessionDao) {
        this.userSessionDao = userSessionDao;
    }

    @Override
    public Optional<UserSession> authenticate(OpenIdCredentials credentials) throws AuthenticationException {
        UUID sessionToken = credentials.getSessionToken();
        UserSession userSession = userSessionDao.loadSession(sessionToken);
        return Optional.fromNullable(userSession);
    }

}
