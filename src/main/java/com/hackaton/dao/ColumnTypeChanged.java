package com.hackaton.dao;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ColumnTypeChanged {

    private String columnName;

    private String dataTypeChangedFrom;

    private String dataTypeChangedTo;

    public ColumnTypeChanged(String columnName, String dataTypeChangedFrom, String dataTypeChangedTo) {
        this.columnName = columnName;
        this.dataTypeChangedFrom = dataTypeChangedFrom;
        this.dataTypeChangedTo = dataTypeChangedTo;
    }

}
