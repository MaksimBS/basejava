package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (searchKey != null) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume);
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract void saveResume(Resume res);

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (searchKey != null) {
            return getResume (searchKey);
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract Resume getResume (Object object);

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (searchKey == null) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(searchKey);
    }

    protected abstract void deleteResume(Object index);

    protected abstract int findIndex(String uuid);

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (searchKey == null) {
            throw new NotExistStorageException(resume.getUuid());
        }
        int index = findIndex(resume.getUuid());
        updateResume(resume, index);
    }

    protected abstract void updateResume(Resume resume, int index);


}
