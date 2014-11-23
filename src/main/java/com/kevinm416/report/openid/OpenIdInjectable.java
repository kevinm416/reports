package com.kevinm416.report.openid;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.kevinm416.report.user.UserSession;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;

public class OpenIdInjectable extends AbstractHttpContextInjectable<UserSession> {

    private static final Logger log = LoggerFactory.getLogger(OpenIdInjectable.class);

    private final Authenticator<OpenIdCredentials, UserSession> authenticator;
    private final Set<Authority> requiredAuthorities;

    public OpenIdInjectable(
            Authenticator<OpenIdCredentials, UserSession> authenticator,
            Set<Authority> requiredAuthorities) {
        this.authenticator = authenticator;
        this.requiredAuthorities = requiredAuthorities;
    }

    @Override
    public UserSession getValue(HttpContext context) {
        UUID sessionToken = getSessionToken(context);
        OpenIdCredentials credentials = new OpenIdCredentials(sessionToken, requiredAuthorities);
        try {
            Optional<UserSession> user = authenticator.authenticate(credentials);
            if (user.isPresent()) {
                return user.get();
            } else {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            log.error("Error authenticating.", e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private static UUID getSessionToken(HttpContext context) {
        Map<String, Cookie> cookieMap = context.getRequest().getCookies();
        if (cookieMap.containsKey(OpenIdConstants.SESSION_TOKEN_KEY)) {
            return UUID.fromString(cookieMap.get(OpenIdConstants.SESSION_TOKEN_KEY).getValue());
        } else {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }

}
