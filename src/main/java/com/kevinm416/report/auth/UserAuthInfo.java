package com.kevinm416.report.auth;

import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.kevinm416.report.user.User;

public class UserAuthInfo implements SaltedAuthenticationInfo {

    private static final long serialVersionUID = 1L;

    private final PrincipalCollection principals;
    private final String hashedSaltedPassword;
    private final ByteSource salt;

    public static UserAuthInfo create(
            User user,
            String realmName,
            String saltedHashedPassword,
            String salt) {
        return new UserAuthInfo(
                new SimplePrincipalCollection(user, realmName),
                saltedHashedPassword,
                ByteSource.Util.bytes(Base64.decode(salt)));
    }

    private UserAuthInfo(
            PrincipalCollection principals,
            String hashedSaltedPassword,
            ByteSource salt) {
        this.principals = principals;
        this.hashedSaltedPassword = hashedSaltedPassword;
        this.salt = salt;
    }

    @Override
    public PrincipalCollection getPrincipals() {
        return principals;
    }

    @Override
    public String getCredentials() {
        return hashedSaltedPassword;
    }

    @Override
    public ByteSource getCredentialsSalt() {
        return salt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((hashedSaltedPassword == null) ? 0 : hashedSaltedPassword
                        .hashCode());
        result = prime * result
                + ((principals == null) ? 0 : principals.hashCode());
        result = prime * result + ((salt == null) ? 0 : salt.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserAuthInfo other = (UserAuthInfo) obj;
        if (hashedSaltedPassword == null) {
            if (other.hashedSaltedPassword != null) {
                return false;
            }
        } else if (!hashedSaltedPassword.equals(other.hashedSaltedPassword)) {
            return false;
        }
        if (principals == null) {
            if (other.principals != null) {
                return false;
            }
        } else if (!principals.equals(other.principals)) {
            return false;
        }
        if (salt == null) {
            if (other.salt != null) {
                return false;
            }
        } else if (!salt.equals(other.salt)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserAuthInfo [principals=" + principals
                + ", hashedSaltedPassword=" + hashedSaltedPassword + ", salt="
                + salt + "]";
    }

}
