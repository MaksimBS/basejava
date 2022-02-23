package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected void saveResume(Resume resume, Object uuid) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveToArray(resume, uuid);
        size++;
    }

    protected abstract void saveToArray(Resume resume, Object uuid);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume getResume(Object searchKey) {
        int index = (int) searchKey;
        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected void deleteResume(Object uuid) {
        int index = (int) uuid;
        deleteFromArray(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteFromArray(int index);

    @Override
    protected void updateResume(Resume resume, Object uuid) {
        int index = (int) uuid;
        storage[index] = resume;
    }

    @Override
    protected boolean checkOnExist(Object uuid) {
        return ((int) uuid >= 0) ? true : false;
    }
}
