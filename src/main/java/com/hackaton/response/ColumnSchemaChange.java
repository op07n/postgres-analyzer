package com.hackaton.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ColumnSchemaChange {

    private String name;

    private ColumnAction action;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String oldType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newType;

    public ColumnSchemaChange(String name, ColumnAction action, String oldType) {
        this.name = name;
        this.action = action;
        this.oldType = oldType;
    }

    public ColumnSchemaChange(String name, ColumnAction action, String oldType, String newType) {
        this(name, action, oldType);
        this.newType = newType;
    }

    @Override
    public String toString() {
        return "ColumnChange{" +
                "name='" + name + '\'' +
                ", action=" + action +
                ", oldType='" + oldType + '\'' +
                ", newType='" + newType + '\'' +
                '}';
    }
}
