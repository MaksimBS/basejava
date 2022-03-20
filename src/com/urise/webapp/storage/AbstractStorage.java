package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public final void save(Resume resume) {
        SK searchKey = getIfNotExist(resume.getUuid());
        saveResume(resume, searchKey);
    }

    protected abstract SK getSearchKey(String uuid);

    protected abstract void saveResume(Resume resume, SK uuid);

    @Override
    public final Resume get(String uuid) {
        SK searchKey = getIfExist(uuid);
        return getResume(searchKey);
    }

    protected abstract Resume getResume(SK uuid);

    protected abstract boolean isExist(SK uuid);

    @Override
    public final void delete(String uuid) {
        SK searchKey = getIfExist(uuid);
        deleteResume(searchKey);
    }

    protected abstract void deleteResume(SK uuid);

    @Override
    public final void update(Resume resume) {
        SK searchKey = getIfExist(resume.getUuid());
        updateResume(resume, searchKey);
    }

    protected abstract void updateResume(Resume resume, SK uuid);

    private SK getIfNotExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getIfExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    abstract protected List<Resume> getAll();
}
