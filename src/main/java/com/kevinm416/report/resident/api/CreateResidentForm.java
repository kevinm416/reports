package com.kevinm416.report.resident.api;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateResidentForm {

    @NotEmpty
    private final String name;
    private final long birthdate;
    private final long houseId;

    @JsonCreator
    public CreateResidentForm(
            @JsonProperty("name") String name,
            @JsonProperty("birthdate") long birthdate,
            @JsonProperty("houseId") long houseId) {
        this.name = name;
        this.birthdate = birthdate;
        this.houseId = houseId;
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
        CreateResidentForm other = (CreateResidentForm) obj;
        if (birthdate != other.birthdate) {
            return false;
        }
        if (houseId != other.houseId) {
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
        return "CreateResidentForm [name=" + name + ", birthdate=" + birthdate
                + ", houseId=" + houseId + "]";
    }

}
