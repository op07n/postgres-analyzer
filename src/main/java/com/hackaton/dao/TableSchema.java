package com.hackaton.dao;

import lombok.Getter;

import java.util.List;

@Getter
public class TableSchema {

    private List<Column> columns;

    private String tableName;

    public TableSchema(String tableName, List<Column> columns) {
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
