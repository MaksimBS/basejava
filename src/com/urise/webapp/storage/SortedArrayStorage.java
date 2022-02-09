package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertNewResume(Resume res, int index) {
        int insertIndex = -index - 1;
        //необходимо сдвинуть массив от индекса элемента вставки
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        updateResume (res, insertIndex);
    }

    protected void updateResume (Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void fillDeletedResume(int index) {
        //сдвигаем весь массив в лево на элемент индекса.
        System.arraycopy(storage, index + 1, storage, index, size - 1);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
