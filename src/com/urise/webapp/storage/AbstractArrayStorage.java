package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[findIndex(uuid)];
        }
        throw new NotExistStorageException(uuid);
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            fillDeletedResume(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT) {
                insertNewResume(resume, index);
                size++;
            } else {
                throw new StorageException("Storage overflow", resume.getUuid());
            }
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    protected abstract void insertNewResume(Resume res, int index);

    protected abstract void fillDeletedResume(int index);

    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }
}
