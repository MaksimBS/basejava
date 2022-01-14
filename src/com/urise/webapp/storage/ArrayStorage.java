package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] allResume = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            allResume[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        allResume[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (allResume[i].getUuid().equals(uuid)) {
                return allResume[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        boolean find = false;
        for (int i = 0; i < size; i++) {
            if (allResume[i].getUuid().equals(uuid) || find) {
                find = true;
                allResume[i] = allResume[i + 1];
            }
        }
        if (find) {
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] copy = new Resume[size];
        System.arraycopy(allResume, 0, copy, 0, size);
        return copy;
    }

    public int size() {
        return size;
    }
}
