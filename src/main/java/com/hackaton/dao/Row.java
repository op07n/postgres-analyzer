package com.hackaton.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Row {

    private BigInteger id;

    public Row(BigInteger id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id='" + id + '\'' +
                '}';
    }
}
