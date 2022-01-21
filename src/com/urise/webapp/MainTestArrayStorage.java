package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid3");
        Resume r3 = new Resume();
        r3.setUuid("uuid2");

        //Entry
        ARRAY_STORAGE.save(r1);
        System.out.println("Entry r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        ARRAY_STORAGE.save(r2);
        System.out.println("Entry r2: " + ARRAY_STORAGE.get(r2.getUuid()));
        ARRAY_STORAGE.save(r3);
        System.out.println("Entry r3: " + ARRAY_STORAGE.get(r3.getUuid()));
        System.out.print("Entry r3 again: ");
        ARRAY_STORAGE.save(r3);

        //Get
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.print("Get dummy: ");
        ARRAY_STORAGE.get("dummy");
        System.out.println();

        //Update
        System.out.print("Update resume r3: ");
        ARRAY_STORAGE.update(r3);
        System.out.println();
        System.out.print("Update new resume: ");
        Resume r4 = new Resume();
        r4.setUuid("uuid4");
        ARRAY_STORAGE.update(r4);

        //delete
        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        ARRAY_STORAGE.delete(r4.getUuid());
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
