package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected final void saveResume(Resume resume, Integer searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveToArray(resume, searchKey);
        size++;
    }

    protected abstract void saveToArray(Resume resume, int index);

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    public final List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    public final int size() {
        return size;
    }

    @Override
    protected final void deleteResume(Integer searchKey) {
        deleteFromArray(searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteFromArray(int index);

    @Override
    protected final void updateResume(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected final boolean isExist(Integer uuid) {
        return uuid >= 0;
    }
}
