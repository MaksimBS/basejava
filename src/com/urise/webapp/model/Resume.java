package com.urise.webapp.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public String getUuid() {
        return uuid;
    }

    public int compareTo(Resume p) {
        int tempResult = fullName.compareTo(p.fullName);
        return tempResult == 0 ? uuid.compareTo(p.uuid) : tempResult;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (uuid.equals(resume.uuid)) {
            return fullName.equals(resume.fullName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }
}
