package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class SortedArrayStorageTest extends AbstractStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    public void fillDeletedResume() {
        //после удаления UUID_1, можно проверить что остались отсортированные UUID_2 и UUID_3
        Assert.assertEquals(storage.getAll()[0], new Resume(UUID_2));
        Assert.assertEquals(storage.getAll()[1], new Resume(UUID_3));
    }

    protected void sortedSave() {
        Assert.assertEquals(storage.getAll()[0], new Resume(UUID_1));
        Assert.assertEquals(storage.getAll()[1], new Resume(UUID_2));
        Assert.assertEquals(storage.getAll()[2], new Resume(UUID_3));
    }

}
