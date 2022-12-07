package com.ultreon.data.types;

import java.io.IOException;
import java.io.ObjectOutputStream;

public interface IType<T> {
    T getValue();
    void setValue(T obj);
    int id();
    void write(ObjectOutputStream stream) throws IOException;
}
