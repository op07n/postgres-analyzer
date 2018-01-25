package com.hackaton.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

public class Tables {

    private List<String> values;

    @JsonCreator
    public Tables(List<String> values) {
        this.values = values;
    }

    @JsonValue
    public List<String> getValues() {
        return values;
    }
}
