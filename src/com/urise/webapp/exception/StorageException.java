package com.urise.webapp.exception;

import java.sql.SQLException;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message) {
        super(message);
        this.uuid = "";
    }

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        this(message, null, e);
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(SQLException e) {
        this(e.getMessage(), e);
    }

    public String getUuid() {
        return uuid;
    }
}
