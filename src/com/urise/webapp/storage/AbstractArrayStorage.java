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
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }


    @Override
    public Resume getByIndex(int index) {
        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected void saveResume(Resume resume, int index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveToArray(resume, index);
        size++;
    }

    protected abstract void saveToArray(Resume resume, int index);

    @Override
    protected void fillDeletedResume(int index) {
        deleteFromArray(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteFromArray(int index);
}
