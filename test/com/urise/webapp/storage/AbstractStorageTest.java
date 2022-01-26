package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.fail;

public abstract class AbstractStorageTest {
    protected Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String DUMMY = "dummy";

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_2));
    }

    @Test
    public void get() {
        Assert.assertEquals(storage.get(UUID_1), new Resume(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(storage.size(), 2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID_4));
        Assert.assertEquals(storage.get(UUID_4), new Resume(UUID_4));
        Assert.assertEquals(storage.get(UUID_4), new Resume(UUID_4));
        Assert.assertEquals(storage.size(), 4);
    }

    @Test
    public void size() {
        Assert.assertEquals(storage.size(), 3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(storage.size(), 0);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(storage.getAll().length, 3);
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_1));
        Assert.assertEquals(storage.get(UUID_1), new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = storage.size(); i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException exception) {
            fail("Error");
        }
        storage.save(new Resume());
    }
}
