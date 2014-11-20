package com.kevinm416.report.server;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import com.google.common.base.Optional;
import com.kevinm416.report.openid.OpenIdCredentials;
import com.kevinm416.report.user.User;
import com.kevinm416.report.user.UserCache;

public class ReportApplicationAuthenticator implements Authenticator<OpenIdCredentials, User> {

    @Override
    public Optional<User> authenticate(OpenIdCredentials credentials) throws AuthenticationException {
        Optional<User> user = UserCache.INSTANCE.getUser(credentials.getSessionToken());
        if (user.isPresent()) {
            return user;
        } else {
            return Optional.absent();
        }
    }

}
