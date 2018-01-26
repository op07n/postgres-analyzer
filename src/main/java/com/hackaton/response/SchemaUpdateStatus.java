package com.hackaton.response;

public enum SchemaUpdateStatus {
    NO_CHANGES(0),
    UPDATED(1),
    VERSION_ERROR(2);

    public final int code;

    SchemaUpdateStatus(int code) {
        this.code = code;
    }

    public boolean isSuccessful() {
        return this != VERSION_ERROR;
    }
}
