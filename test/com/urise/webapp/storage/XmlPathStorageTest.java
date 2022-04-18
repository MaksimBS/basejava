package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.XmlStreamSerializer;

import java.io.File;

public class XmlPathStorageTest extends AbstractStorageTest{
    public XmlPathStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new XmlStreamSerializer()));
    }
}
