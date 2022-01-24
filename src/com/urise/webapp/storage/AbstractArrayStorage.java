package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[findIndex(uuid)];
        }
        System.out.println("Resume " + uuid + " is not found");
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " is not found");
        } else {
            fillDeletedResume(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT) {
                insertNewResume(resume, index);
                size++;
            } else {
                System.out.println("Resume bank is full");
            }
        } else {
            System.out.println("This resume " + resume.getUuid() + " is already in the bank");
        }
    }

    protected abstract void insertNewResume(Resume res, int index);

    protected abstract void fillDeletedResume(int index);

    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
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
