package com.kevinm416.report.auth;

import com.kevinm416.report.user.User;

public class UserWithAuthInfo {

    private final User user;
    private final UserAuthInfo authInfo;

    public UserWithAuthInfo(User user, UserAuthInfo authInfo) {
        this.user = user;
        this.authInfo = authInfo;
    }

    public User getUser() {
        return user;
    }

    public UserAuthInfo getAuthInfo() {
        return authInfo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((authInfo == null) ? 0 : authInfo.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        UserWithAuthInfo other = (UserWithAuthInfo) obj;
        if (authInfo == null) {
            if (other.authInfo != null) {
                return false;
            }
        } else if (!authInfo.equals(other.authInfo)) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserWithAuthInfo [user=" + user + ", authInfo=" + authInfo
                + "]";
    }

}
