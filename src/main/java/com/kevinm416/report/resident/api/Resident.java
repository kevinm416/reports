package com.kevinm416.report.resident.api;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kevinm416.report.common.Identifiable;

public class Resident implements Identifiable {

    private final long id;
    @NotNull
    private final String name;
    private final long birthdate;
    private final long houseId;

    @JsonCreator
    public Resident(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name,
            @JsonProperty("birthdate") long birthdate,
            @JsonProperty("houseId") long houseId) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.houseId = houseId;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public long getHouseId() {
        return houseId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (birthdate ^ (birthdate >>> 32));
        result = prime * result + (int) (houseId ^ (houseId >>> 32));
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
        Resident other = (Resident) obj;
        if (birthdate != other.birthdate) {
            return false;
        }
        if (houseId != other.houseId) {
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
        return "Resident [id=" + id + ", name=" + name + ", birthdate="
                + birthdate + ", houseId=" + houseId + "]";
    }

}
