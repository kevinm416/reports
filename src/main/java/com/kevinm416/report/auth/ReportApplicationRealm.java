package com.kevinm416.report.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;

import com.kevinm416.report.user.UserDAO;

public class ReportApplicationRealm implements Realm {

    public static final String REALM_NAME = "ReportApplicationRealm";

    private final UserDAO residentCoordinatorDAO;
    private final CredentialsMatcher credentialsMatcher;

    public static ReportApplicationRealm create(UserDAO residentCoordinatorDAO) {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(PasswordHasher.ALGORITHM_NAME);
        credentialsMatcher.setHashIterations(PasswordHasher.HASH_ITERATIONS);
        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        return new ReportApplicationRealm(residentCoordinatorDAO, credentialsMatcher);
    }

    private ReportApplicationRealm(
            UserDAO residentCoordinatorDAO,
            CredentialsMatcher credentialsMatcher) {
        this.residentCoordinatorDAO = residentCoordinatorDAO;
        this.credentialsMatcher = credentialsMatcher;
    }

    @Override
    public String getName() {
        return "ReportApplicationRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String name = usernamePasswordToken.getUsername();
        UserWithAuthInfo userWithAuthInfo =
                residentCoordinatorDAO.loadAuthenticationInformation(name);

        if (credentialsMatcher.doCredentialsMatch(token, userWithAuthInfo.getAuthInfo())) {
            return userWithAuthInfo.getAuthInfo();
        } else {
            String message = "Submitted credentials for token [" + token + "] did not match the expected credentials.";
            throw new IncorrectCredentialsException(message);
        }
    }

}
