package com.kevinm416.report.openid;

import java.util.Set;
import java.util.UUID;

public class OpenIdCredentials {

    private final UUID sessionToken;
    private final Set<Authority> requiredAuthorities;

    public OpenIdCredentials(UUID sessionToken,
            Set<Authority> requiredAuthorities) {
        this.sessionToken = sessionToken;
        this.requiredAuthorities = requiredAuthorities;
    }

    public UUID getSessionToken() {
        return sessionToken;
    }

    public Set<Authority> getRequiredAuthorities() {
        return requiredAuthorities;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((requiredAuthorities == null) ? 0 : requiredAuthorities
                        .hashCode());
        result = prime * result
                + ((sessionToken == null) ? 0 : sessionToken.hashCode());
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
        OpenIdCredentials other = (OpenIdCredentials) obj;
        if (requiredAuthorities == null) {
            if (other.requiredAuthorities != null) {
                return false;
            }
        } else if (!requiredAuthorities.equals(other.requiredAuthorities)) {
            return false;
        }
        if (sessionToken == null) {
            if (other.sessionToken != null) {
                return false;
            }
        } else if (!sessionToken.equals(other.sessionToken)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OpenIdCredentials [sessionToken=" + sessionToken
                + ", requiredAuthorities=" + requiredAuthorities + "]";
    }

}
