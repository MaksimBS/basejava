package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage.get(index);
        }
        return null;
    }

    @Override
    protected void saveResume(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume (Object object) {
        return (Resume) object;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }

    protected void updateResume(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteResume(Object object) {
        storage.remove(object);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }
}
