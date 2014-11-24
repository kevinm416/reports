package com.kevinm416.report.server;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import com.google.common.base.Optional;

public class ReportApplicationAuthenticator implements Authenticator<BasicCredentials, User> {

    @Override
    public Optional<User> authenticate(BasicCredentials credentials)
            throws AuthenticationException {
        if ("password".equals(credentials.getPassword())) {
            return Optional.of(new User(credentials.getUsername()));
        } else {
            return Optional.absent();
        }
    }

}
