package com.kevinm416.report.auth;

import java.util.Collections;

import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

public class ReportApplicationShiroFilter extends AbstractShiroFilter {

    private final Realm realm;

    public ReportApplicationShiroFilter(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void init() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationListeners(
                Collections.<AuthenticationListener>singleton(new ReportApplicationAuthenticationListener()));

        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setAuthenticator(authenticator);
        webSecurityManager.setRealm(realm);
        setSecurityManager(webSecurityManager);

        FilterChainManager filterChainManager = new DefaultFilterChainManager();
        filterChainManager.addFilter("reportApplicationAuth", new ReportApplicationAuthFilter());
        filterChainManager.createChain("/*", "reportApplicationAuth");

        PathMatchingFilterChainResolver filterChainResolver = new PathMatchingFilterChainResolver();
        filterChainResolver.setFilterChainManager(filterChainManager);

        setFilterChainResolver(filterChainResolver);
    }

}
