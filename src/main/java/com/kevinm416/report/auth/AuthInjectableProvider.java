package com.kevinm416.report.auth;

import org.apache.shiro.SecurityUtils;

import com.google.common.base.Preconditions;
import com.kevinm416.report.user.User;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

public class AuthInjectableProvider implements InjectableProvider<Auth, Parameter> {

    private final Injectable<User> INJECTABLE = new Injectable<User>() {
        @Override
        public User getValue() {
            User rc = (User) SecurityUtils.getSubject().getPrincipal();
            return Preconditions.checkNotNull(rc);
        }
    };

    @Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

    @Override
    public Injectable<User> getInjectable(ComponentContext ic, Auth a, Parameter c) {
        if (c.getParameterClass() == User.class) {
            return INJECTABLE;
        } else {
            throw new IllegalArgumentException(c.getParameterClass() + " does not match " + User.class);
        }
    }

}
