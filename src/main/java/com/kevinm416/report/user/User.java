package com.kevinm416.report.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kevinm416.report.common.Identifiable;

public class User implements Identifiable {

    private final long id;
    private final String name;
    private final boolean admin;

    @JsonCreator
    public User(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name,
            @JsonProperty("admin") boolean admin) {
        this.id = id;
        this.name = name;
        this.admin = admin;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (admin ? 1231 : 1237);
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        User other = (User) obj;
        if (admin != other.admin) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", admin=" + admin + "]";
    }

}
