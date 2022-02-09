package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    protected void insertNewResume(Resume Res, int index) {
        storage[size] = Res;
    }

    protected void updateResume (Resume Res, int index) {
        storage[index] = Res;
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void fillDeletedResume(int index) {
        storage[index] = storage[size - 1];
    }
}
