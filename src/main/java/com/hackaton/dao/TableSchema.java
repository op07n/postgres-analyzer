package com.hackaton.dao;

import lombok.Getter;

import java.util.List;

@Getter
public class TableSchema {

    private int version;

    private List<Column> columns;

    private List<Row> rows;

    private String tableName;

    public TableSchema(int version, String tableName, List<Column> columns) {
        this.version = version;
        this.columns = columns;
        this.tableName = tableName;
    }

    public TableSchema(String tableName, List<Column> columns, List<Row> rows) {
        this.rows = rows;
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "TableSchema{" +
                "columns=" + columns +
                "rows=" + rows +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
