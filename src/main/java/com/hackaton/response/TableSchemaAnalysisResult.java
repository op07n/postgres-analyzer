package com.hackaton.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hackaton.dao.Column;
import com.hackaton.dao.ColumnTypeChanged;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TableSchemaAnalysisResult {

    private Integer oldVersion;

    private Integer newVersion;

    private SchemaUpdateStatus schemaUpdateStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Column> columnAdded;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Column> columnDeleted;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ColumnTypeChanged> columnTypeChanged;

    public TableSchemaAnalysisResult(Integer oldVersion, Integer newVersion, SchemaUpdateStatus schemaUpdateStatus) {
        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.schemaUpdateStatus = schemaUpdateStatus;
    }

    public TableSchemaAnalysisResult(Integer oldVersion, Integer newVersion, SchemaUpdateStatus schemaUpdateStatus, List<Column> columnAdded, List<Column> columnDeleted, List<ColumnTypeChanged> columnTypeChanged) {
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
