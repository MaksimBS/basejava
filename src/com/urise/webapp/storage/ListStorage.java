package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public Resume getByIndex(int index) {
        return storage.get(index);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        storage.add(resume);
    }

    protected void updateResume(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteFromArray(int index) {
        storage.remove(index);
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
