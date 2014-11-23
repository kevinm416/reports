package com.kevinm416.report.user;

import java.util.UUID;

public class UserSession {

    private final UUID id;
    private final long userId;

    public UserSession(UUID id, long userId) {
        this.id = id;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (int) (userId ^ (userId >>> 32));
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
        UserSession other = (UserSession) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserSession [id=" + id + ", userId=" + userId + "]";
    }

}
