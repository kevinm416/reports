package com.kevinm416.report.auth;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class ReportApplicationAuthFilter extends FormAuthenticationFilter{

    private static final String LOGIN_URL = "/login.html";
    private static final String SUCCESS_URL = "/";

    public ReportApplicationAuthFilter() {
        setLoginUrl(LOGIN_URL);
        setSuccessUrl(SUCCESS_URL);
    }

}
