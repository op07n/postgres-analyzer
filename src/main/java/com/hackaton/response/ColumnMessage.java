package com.hackaton.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ColumnMessage {

    private final String column;

    private final String message;

    public ColumnMessage(String column, String error) {
        this.column = column;
        this.message = error;
    }
}
