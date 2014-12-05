package com.kevinm416.report.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportApplicationAuthenticationListener implements AuthenticationListener{
    private static final Logger log = LoggerFactory.getLogger(ReportApplicationAuthenticationListener.class);

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        // No-op
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
        log.error("Error during authentication: " + token, ae);
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        // No-op
    }

}
