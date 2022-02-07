package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int findIndex(String uuid);

    protected abstract void insertNewResume(Resume res, int index);

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return getbyIndex(index);
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract Resume getbyIndex(int index);

    protected abstract void fillDeletedResume(int index);

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        setResume(resume, index);
    }

    protected abstract void setResume(Resume resume, int index);
}
