package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume resume) {
        if (storage.contains(resume)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertNewResume(resume,0);
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertNewResume(Resume res, int index);

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage.get(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            fillDeletedResume(index);
        }
    }

    protected abstract void fillDeletedResume(int index);

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    public int size() {
        return storage.size();
    }

    @Override
    public void update(Resume resume) {
        int index = storage.indexOf(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage.set(index, resume);
        }
    }
}
