package com.urise.webapp.util;

public class LazySingleton {
    private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    private static class LazySingletonHolder {

        private static final LazySingleton INSTANCE = new LazySingleton();
        public static LazySingleton getInstance() {
            return LazySingletonHolder.INSTANCE;
        }
    }
}