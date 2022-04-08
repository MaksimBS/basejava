package com.urise.webapp.storage;

import java.nio.file.Path;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage<Path>(STORAGE_DIR));
    }
}