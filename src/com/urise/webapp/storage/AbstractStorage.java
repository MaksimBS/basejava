package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (checkOnExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume, searchKey);
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract void saveResume(Resume resume, Object uuid);

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (checkOnExist(searchKey)) {
            return getResume (searchKey);
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract Resume getResume (Object uuid);

    protected abstract boolean checkOnExist (Object uuid);

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (checkOnExist(searchKey) == false) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(searchKey);
    }

    protected abstract void deleteResume(Object uuid);

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (checkOnExist(searchKey) == false) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateResume(resume, searchKey);
    }

    protected abstract void updateResume(Resume resume, Object uuid);
}
