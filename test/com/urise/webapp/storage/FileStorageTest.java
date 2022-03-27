package com.urise.webapp.storage;

import java.io.File;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new AbstractFileStorage(new File("C:/Java/testStorage")));
    }
}
