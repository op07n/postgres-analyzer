package com.hackaton.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Column {

    private String columnName;

    private String dataType;

    public Column(String columnName, String dataType) {
        this.columnName = columnName;
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
