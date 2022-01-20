package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] allResume = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public Resume get(String uuid) {
        try {
            return allResume[findIndex(uuid)];
        } catch (Exception e) {
            System.out.println("Resume is not found");
            return null;
        }
    }

    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(allResume, 0, size, searchKey);
    }

    public int size() {
        return size;
    }
}
