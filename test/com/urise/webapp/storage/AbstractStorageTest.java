package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


public abstract class AbstractStorageTest {
    protected Storage storage;
    public static final String UUID_1 = "uuid1";
    public static final Resume r1 = new Resume(UUID_1);
    public static final String UUID_2 = "uuid2";
    public static final Resume r2 = new Resume(UUID_2);
    public static final String UUID_3 = "uuid3";
    public static final Resume r3 = new Resume(UUID_3);
    public static final String UUID_4 = "uuid4";
    public static final Resume r4 = new Resume(UUID_4);
    public static final String DUMMY = "dummy";

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    public void get() {
        assertEquals(r1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
    }

    @Test
    public void save() {
        storage.save(r4);
        assertEquals(4, storage.size());
        Resume rf = storage.get(UUID_4);
        assertEquals(r4, rf);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(r1);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] actual = storage.getAll();
        Resume[] expected = {r1, r2, r3};

        assertEquals(3, actual.length);
        Assert.assertArrayEquals( expected, actual);
    }

    @Test
    public void update() {
        storage.update(r1);
        assertSame(r1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(DUMMY));
    }
}
