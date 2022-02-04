package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ListStorage extends AbstractStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }

    @Override
    protected void insertNewResume(Resume res, int index) {
        storage.add(res);
    }

    @Override
    protected void fillDeletedResume(int index) {
        storage.remove(index);
    }
}
