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
        try {
            getIndex(resume.getUuid());
            System.out.println("This resume is already in the bank");
        } catch (Exception e) {
            if (size < allResume.length) {
                allResume[size] = resume;
                size++;
            } else {
                System.out.println("Resume bank is full");
            }
        }
    }

    public Resume get(String uuid) {
        try {
            return allResume[getIndex(uuid)];
        } catch (Exception e) {
            System.out.println("Resume is not found");
            return null;
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (allResume[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        throw new IllegalArgumentException("arguments not found");
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
        } else {
            System.out.println("Resume is not found");
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

    public void update(Resume resume) {
        try {
            allResume[getIndex(resume.getUuid())] = resume;
        } catch (Exception e) {
            System.out.println("Resume is not found");
        }
    }
}
