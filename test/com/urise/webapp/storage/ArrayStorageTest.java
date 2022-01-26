package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class ArrayStorageTest extends AbstractStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    public void fillDeletedResume() {
        //после удаления UUID_1, можно проверить что остались UUID_2 и UUID_3
        Assert.assertEquals(storage.get(UUID_2), new Resume(UUID_2));
        Assert.assertEquals(storage.get(UUID_3), new Resume(UUID_3));
    }

    protected void sortedSave() {
    }

}
