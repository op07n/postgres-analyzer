package com.hackaton.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TableSchemaAnalysisResult {

    private Integer oldVersion;

    private Integer newVersion;

    private String tableName;

    private SchemaUpdateStatus schemaUpdateStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ColumnSchemaChange> columnChanges;

    public TableSchemaAnalysisResult(Integer oldVersion, Integer newVersion, String tableName, SchemaUpdateStatus schemaUpdateStatus) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.tableName = tableName;
        this.schemaUpdateStatus = schemaUpdateStatus;
    }

    public TableSchemaAnalysisResult(Integer oldVersion, Integer newVersion, String tableName, SchemaUpdateStatus schemaUpdateStatus, List<ColumnSchemaChange> columnChanges) {
        this(oldVersion, newVersion, tableName, schemaUpdateStatus);
        this.columnChanges = columnChanges;
    }

    @Override
    public String toString() {
        return "TableSchemaAnalysisResult{" +
                "oldVersion=" + oldVersion +
                ", newVersion=" + newVersion +
                ", columnChanges=" + columnChanges +
                '}';
    }
}
