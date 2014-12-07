package com.kevinm416.report.auth;

public class HashedPassword {

    private final String hashedPassword;
    private final String salt;

    public HashedPassword(String hashedPassword, String salt) {
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((hashedPassword == null) ? 0 : hashedPassword.hashCode());
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
        HashedPassword other = (HashedPassword) obj;
        if (hashedPassword == null) {
            if (other.hashedPassword != null) {
                return false;
            }
        } else if (!hashedPassword.equals(other.hashedPassword)) {
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
        return "HashedPassword [hashedPassword=" + hashedPassword + ", salt="
                + salt + "]";
    }

}
