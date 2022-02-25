package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object searchKey = getIfNotExist(resume.getUuid());
        saveResume(resume, searchKey);
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract void saveResume(Resume resume, Object uuid);

    @Override
    public Resume get(String uuid) {
        Object searchKey = getIfExist(uuid);
        return getResume(searchKey);
    }

    protected abstract Resume getResume(Object uuid);

    protected abstract boolean isExist(Object uuid);

    @Override
    public void delete(String uuid) {
        Object searchKey = getIfExist(uuid);
        deleteResume(searchKey);
    }

    protected abstract void deleteResume(Object uuid);

    @Override
    public void update(Resume resume) {
        Object searchKey = getIfExist(resume.getUuid());
        updateResume(resume, searchKey);
    }

    protected abstract void updateResume(Resume resume, Object uuid);

    private Object getIfNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getIfExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
