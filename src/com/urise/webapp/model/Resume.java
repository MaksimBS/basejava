package com.urise.webapp.model;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public int compareTo (Resume p) {
        return this.uuid.compareTo(p.uuid);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
