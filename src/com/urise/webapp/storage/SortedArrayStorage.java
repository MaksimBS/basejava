package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected void saveToArray(Resume resume, int index) {
        int insertIndex = -(int) index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        updateResume(resume, insertIndex);
    }

    @Override
    protected void deleteFromArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1);
    }
}
