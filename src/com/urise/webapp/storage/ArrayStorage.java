package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    protected void saveToArray(Resume resume, int index) {
        storage[size] = resume;
    }

    protected void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void deleteFromArray(int index) {
        storage[index] = storage[size - 1];
    }
}
