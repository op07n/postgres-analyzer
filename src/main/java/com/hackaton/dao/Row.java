package com.hackaton.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Row {

    private List<Object> object;


    public Row(List<Object> object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Row{" +
                "value='" + object.toString() + '\'' +
                '}';
    }
}
