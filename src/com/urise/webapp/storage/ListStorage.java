package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void saveResume(Resume resume, Object uuid) {
        storage.add(resume);
    }

    @Override
    protected Resume getResume(Object uuid) {
        return storage.get((int) uuid);
    }

    protected void updateResume(Resume resume, Object searchKey) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected void deleteResume(Object uuid) {
        storage.remove((int) uuid);
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

    @Override
    protected boolean checkOnExist(Object uuid) {
        return ((int) uuid >= 0) ? true : false;
    }
}
