package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    private static final int STORAGE_LIMIT = 10000;


    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected void insertNewResume(Resume Res, int index) {
        storage[size] = Res;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " is not found");
            return null;
        }
        return storage[index];

    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void fillDelitedElement(int index) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Resume " + resume.getUuid() + " is not found");
        } else {
            storage[index] = resume;
        }
    }
}
