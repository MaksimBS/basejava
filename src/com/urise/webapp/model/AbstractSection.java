package com.urise.webapp.model;

import java.io.Serializable;

abstract public class AbstractSection<T> implements Serializable {
    private T info;

    public T getInfo() {
        return info;
    }
}
