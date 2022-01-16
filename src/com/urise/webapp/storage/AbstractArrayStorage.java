package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] allResume = new Resume[STORAGE_LIMIT];
    protected int size = 0;


    @Override
    public void clear() {

    }

    @Override
    public void save(Resume resume) {

    }

    @Override
    public Resume get(String uuid) {
        try {
            return allResume[getIndex(uuid)];
        } catch (Exception e) {
            System.out.println("Resume is not found");
            return null;
        }
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        Arrays.sort(allResume, 0, size);
        int index = Arrays.binarySearch(allResume, 0, size, searchKey);
        if (index >= 0) {
            return index;
        }
        throw new IllegalArgumentException("Resume not found");
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    public int size() {
        return size;
    }

    @Override
    public void update(Resume resume) {

    }
}
