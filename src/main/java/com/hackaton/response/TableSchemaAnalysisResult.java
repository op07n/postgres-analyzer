package com.hackaton.response;

import com.hackaton.dao.Column;
import lombok.Getter;

import java.util.List;

@Getter
public class TableSchemaAnalysisResult {

    private Integer oldVersion;

    private Integer newVersion;

    private SchemaUpdateStatus schemaUpdateStatus;

    private List<Column> columnAdded;

    private List<Column> columnDeleted;

    private List<Column> columnTypeChanged;

    public TableSchemaAnalysisResult(Integer oldVersion, Integer newVersion, SchemaUpdateStatus schemaUpdateStatus) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.schemaUpdateStatus = schemaUpdateStatus;
    }

    public TableSchemaAnalysisResult(Integer oldVersion, Integer newVersion, SchemaUpdateStatus schemaUpdateStatus, List<Column> columnAdded, List<Column> columnDeleted, List<Column> columnTypeChanged) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.schemaUpdateStatus = schemaUpdateStatus;
        this.columnAdded = columnAdded;
        this.columnDeleted = columnDeleted;
        this.columnTypeChanged = columnTypeChanged;
    }

    @Override
    public String toString() {
        return "TableSchemaAnalysisResult{" +
                "oldVersion=" + oldVersion +
                ", newVersion=" + newVersion +
                ", columnAdded=" + columnAdded +
                ", columnDeleted=" + columnDeleted +
                ", columnTypeChanged=" + columnTypeChanged +
                '}';
    }
}
