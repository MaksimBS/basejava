package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT & size == Math.abs(index) - 1) {
                //запись в конец массива, не выходит за пределы массива
                storage[size] = resume;
                size++;
            } else if (size < STORAGE_LIMIT) {
                //запись в середину массива
                int x = Math.abs(index);
                Resume[] tempStorage = Arrays.copyOfRange(storage, Math.abs(index) - 1, size);
                storage[Math.abs(index) - 1] = resume;
                System.arraycopy(tempStorage, 0, storage, Math.abs(index), tempStorage.length);
                size++;
            } else {
                //нет места для записи
                System.out.println("Resume bank is full");
            }
        } else {
            System.out.println("This resume " + resume.getUuid() + " is already in the bank");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " is not found");
        } else {
            //делаем копию массива от индекса и до конца (все +1)
            //потом сдвигаем в рабочий массив.
            Resume[] tempStorage = Arrays.copyOfRange(storage, index + 1, size + 1);
            System.arraycopy(tempStorage, 0, storage, index, tempStorage.length);
            size--;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
