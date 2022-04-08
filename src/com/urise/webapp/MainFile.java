package com.urise.webapp;

import java.io.File;
import java.util.Arrays;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("./src/com/urise/webapp");
        printDirectory(dir,4);
    }

    public static void printDirectory(File dir, int Raw) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(buildString('-',Raw) + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(buildString('-',Raw) + "Directory: " + file.getName());
                    printDirectory(file, Raw*2);
                }
            }
        }
    }

    static String buildString(char c, int n) {
        char[] arr = new char[n];
        Arrays.fill(arr, c);
        return new String(arr);
    }
}
