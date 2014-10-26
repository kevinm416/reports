package com.kevinm416.report.house;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateHouseForm {

    private final String name;

    @JsonCreator
    public CreateHouseForm(
            @JsonProperty("name") String name) {
        this.name = name;
    }

}
