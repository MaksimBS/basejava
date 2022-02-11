package com.urise.webapp;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestSortedArrayStorage {
    static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid3");
        Resume r3 = new Resume("uuid2");

        //Entry
        ARRAY_STORAGE.save(r1);
        System.out.println("Entry r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        ARRAY_STORAGE.save(r2);
        System.out.println("Entry r2: " + ARRAY_STORAGE.get(r2.getUuid()));
        ARRAY_STORAGE.save(r3);
        System.out.println("Entry r3: " + ARRAY_STORAGE.get(r3.getUuid()));
        System.out.print("Entry r3 again: ");
        try {
            ARRAY_STORAGE.save(r3);
        } catch (ExistStorageException e) {
            System.out.println(e);
        }

        //Get
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.print("Get dummy: ");
        try {
            ARRAY_STORAGE.get("dummy");
        } catch (NotExistStorageException e) {
            System.out.println(e);
        }
        System.out.println();

        //Update
        System.out.print("Update resume r3: ");
        ARRAY_STORAGE.update(r3);
        System.out.println();
        System.out.print("Update new resume: ");
        Resume r4 = new Resume("uuid4");
        try {
            ARRAY_STORAGE.update(r4);
        } catch (NotExistStorageException e) {
            System.out.println(e);
        }

        //delete
        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        try {
            ARRAY_STORAGE.delete(r4.getUuid());
        } catch (NotExistStorageException e) {
            System.out.println(e);
        }
        printAll();

        //clear
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume resume : ARRAY_STORAGE.getAll()) {
            System.out.println(resume);
        }
    }
}
