package com.kevinm416.report.openid;

import io.dropwizard.auth.Authenticator;

import java.util.EnumSet;
import java.util.Set;

import com.google.common.collect.Lists;
import com.kevinm416.report.user.UserSession;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

public class OpenIdAuthProvider implements InjectableProvider<RestrictedTo, Parameter> {

    private final Authenticator<OpenIdCredentials, UserSession> authenticator;

    public OpenIdAuthProvider(Authenticator<OpenIdCredentials, UserSession> authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

    @Override
    public Injectable<?> getInjectable(ComponentContext ic, RestrictedTo r, Parameter c) {
        return new OpenIdInjectable(authenticator, getAuthoritySet(r));
    }

    private Set<Authority> getAuthoritySet(RestrictedTo r) {
        return EnumSet.copyOf(Lists.newArrayList(r.value()));
    }

}
