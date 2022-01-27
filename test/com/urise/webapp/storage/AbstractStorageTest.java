package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;


public abstract class AbstractStorageTest {
    protected Storage storage;
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";
    public static final String DUMMY = "dummy";

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void get() {
        assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        try {
            storage.delete(UUID_1);
            fail("Повторное удаление, без проблем");
        } catch (NotExistStorageException thrown) {
            assertNotEquals("", thrown.getMessage());
        }

        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
    }

    @Test
    public void save() {
        assertEquals(3, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void ExistStorageSeve() {
        storage.save(new Resume(UUID_1));
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
        assertEquals(3, storage.getAll().length);
        List<Resume> tempStorage = Arrays.asList(new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3));
        assertEquals(Arrays.asList(storage.getAll()), tempStorage);

    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_1));
        assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = storage.size(); i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException exception) {
            fail("Переполнение раньше времени");
        }
        storage.save(new Resume());
    }
}
