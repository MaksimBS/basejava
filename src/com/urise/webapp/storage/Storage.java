package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void clear();

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();

    void update(Resume resume);
}