package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

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

    private int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        Arrays.sort(allResume, 0, size);
        int index = Arrays.binarySearch(allResume, 0, size, searchKey);
        if (index >= 0) {
            return index;
        }
        throw new IllegalArgumentException("Resume not found");
    }

    public Resume get(String uuid) {
        try {
            return allResume[getIndex(uuid)];
        } catch (Exception e) {
            System.out.println("Resume is not found");
            return null;
        }
    }

    public void delete(String uuid) {
        try {
            allResume[getIndex(uuid)] = allResume[size - 1];
            allResume[size - 1] = null;
            size--;
        } catch (Exception e) {
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
