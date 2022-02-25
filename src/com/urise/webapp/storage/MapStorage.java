package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void saveResume(Resume resume, Object uuid) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object uuid) {
        return storage.get((String) uuid);
    }

    protected void updateResume(Resume resume, Object uuid) {
        storage.put((String) uuid, resume);
    }

    @Override
    protected void deleteResume(Object uuid) {
        storage.remove((String) uuid);
    }

    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    protected boolean isExist(Object uuid) {
        return storage.containsKey((String) uuid);
    }
}
