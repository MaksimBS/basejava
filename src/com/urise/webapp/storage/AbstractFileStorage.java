package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AbstractFileStorage<F> extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteResume(file);
            }
        }
    }

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        writeFile(resume, file);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            writeFile(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        //создание резюме из файла.
        //пока создаю для теста.
        return readFile(file);
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> resumes = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                resumes.add(getResume(file));
            }
        }
        return resumes;
    }


// дальше для теста, в дальнейшем переделаю.
    protected void writeFile(Resume resume, File file) {
        //тестовое заполнение файла.
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(resume.getFullName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Resume readFile(File file) {
        String fullName = "";

        try (FileReader testFile = new FileReader(file)) {
            try (BufferedReader reader = new BufferedReader(testFile)) {
                fullName = reader.readLine();
                //reader.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Resume(file.getName(),fullName);
        /*
        try {
            FileReader testFile = new FileReader(file);
            BufferedReader reader = new BufferedReader(testFile);
            fullName = reader.readLine();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Resume(file.getName(),fullName);
         */
    }


}