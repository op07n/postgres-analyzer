package com.hackaton.response;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import java.util.List;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AnalysisMessage {

    @Getter
    private final String tableName;

    @Getter
    private final List<ColumnMessage> columnMessages;

    public AnalysisMessage(String tableName, List<ColumnMessage> columnMessages) {
        this.tableName = tableName;
        this.columnMessages = columnMessages;
    }
}
