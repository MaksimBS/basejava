package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamInOut;

import java.io.File;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {

        super(new FileStorage(new File(STORAGE_DIR), new ObjectStreamInOut()));
    }
}