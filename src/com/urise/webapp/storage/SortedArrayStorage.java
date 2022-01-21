package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertNewResume(Resume res, int index) {
        if (size == Math.abs(index) - 1) {
            //запись в конец массива, не выходит за пределы массива
            storage[size] = res;
        } else {
            //запись в середину массива
            Resume[] tempStorage = Arrays.copyOfRange(storage, Math.abs(index) - 1, size);
            storage[Math.abs(index) - 1] = res;
            System.arraycopy(tempStorage, 0, storage, Math.abs(index), tempStorage.length);
        }
    }

    @Override
    protected void fillDelitedResume(int index) {
        //делаем копию массива от индекса и до конца (все +1)
        //потом сдвигаем в рабочий массив.
        Resume[] tempStorage = Arrays.copyOfRange(storage, index + 1, size + 1);
        System.arraycopy(tempStorage, 0, storage, index, tempStorage.length);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
