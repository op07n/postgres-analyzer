package com.hackaton.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ConnectionConfig {

    private  String dbUrl;
    private  String username;
    private  String password;
    private  String schemaName;

    @JsonCreator
    public ConnectionConfig(@JsonProperty("dbUrl") String dbUrl, @JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("schemaName") String schemaName) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
        this.schemaName = schemaName;
    }

    @Override
    public String toString() {
        return "ConnectionConfig{" +
                "dbUrl='" + dbUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='******" + '\'' +
                ", schemaName='" + schemaName + '\'' +
                '}';
    }
}
