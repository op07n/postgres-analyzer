package com.hackaton.dao;

import lombok.Getter;

import java.util.List;

@Getter
public class TableSchema {

    private int version;

    private List<Column> columns;

    private String tableName;

    public TableSchema(int version, String tableName, List<Column> columns) {
        this.version = version;
        this.columns = columns;
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "TableSchema{" +
                "columns=" + columns +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
