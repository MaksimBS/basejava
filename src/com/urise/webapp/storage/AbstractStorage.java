package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public void save(Resume resume) {
        Object object = getObject(resume.getUuid());
        if (object != null) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume);
    }

    protected abstract Object getObject(String uuid);

    protected abstract void saveResume(Resume res);

    @Override
    public Resume get(String uuid) {
        Object object = getObject(uuid);
        if (object != null) {
            return getByObject(object);
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract Resume getByObject(Object object);

    @Override
    public void delete(String uuid) {
        Object object = getObject(uuid);
        if (object == null) {
            throw new NotExistStorageException(uuid);
        }
        fillDeletedResume(object);
    }

    protected abstract void fillDeletedResume(Object object);

    protected abstract int findIndex(String uuid);

    @Override
    public void update(Resume resume) {
        Object object = getObject(resume.getUuid());
        if (object == null) {
            throw new NotExistStorageException(resume.getUuid());
        }
        int index = findIndex(resume.getUuid());
        updateResume(resume, index);
    }

    protected abstract void updateResume(Resume resume, int index);


}
