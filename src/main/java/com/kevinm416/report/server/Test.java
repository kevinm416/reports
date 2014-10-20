package com.kevinm416.report.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {

    private final String test;

    @JsonCreator
    public Test(@JsonProperty("test") String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((test == null) ? 0 : test.hashCode());
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
        Test other = (Test) obj;
        if (test == null) {
            if (other.test != null) {
                return false;
            }
        } else if (!test.equals(other.test)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Test [test=" + test + "]";
    }

}
