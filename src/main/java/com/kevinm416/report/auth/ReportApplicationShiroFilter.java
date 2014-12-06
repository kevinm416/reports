package com.kevinm416.report.auth;

import java.util.Collections;

import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

public class ReportApplicationShiroFilter extends AbstractShiroFilter {

    private static final String LOGIN_URL = "/login.html";
    private static final String SUCCESS_URL = "/index.html";

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
        FormAuthenticationFilter formAuth = new FormAuthenticationFilter();
        formAuth.setLoginUrl(LOGIN_URL);
        formAuth.setSuccessUrl(SUCCESS_URL);

        filterChainManager.addFilter("reportApplicationAuth", formAuth);
        filterChainManager.createChain("/js/**", "anon");
        filterChainManager.createChain("/css/**", "anon");
        filterChainManager.createChain("/lib/**", "anon");
        filterChainManager.createChain("/favicon.ico", "anon");
        filterChainManager.createChain("/*", "reportApplicationAuth");


        PathMatchingFilterChainResolver filterChainResolver = new PathMatchingFilterChainResolver();
        filterChainResolver.setFilterChainManager(filterChainManager);

        setFilterChainResolver(filterChainResolver);
    }

}
