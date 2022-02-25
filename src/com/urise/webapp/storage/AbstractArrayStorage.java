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
    protected void saveResume(Resume resume, Object index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveToArray(resume, (int) index);
        size++;
    }

    protected abstract void saveToArray(Resume resume, int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected void deleteResume(Object index) {
        deleteFromArray((int) index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteFromArray(int index);

    @Override
    protected void updateResume(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    protected boolean isExist(Object uuid) {
        return (int) uuid >= 0;
    }
}
