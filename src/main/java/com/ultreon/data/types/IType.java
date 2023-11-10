package com.ultreon.data.types;

import java.io.DataOutputStream;
import java.io.IOException;

public interface IType<T> {
    T getValue();

    void setValue(T obj);

    int id();

    void write(DataOutputStream stream) throws IOException;

    boolean equals(Object other);

    int hashCode();

    IType<T> copy();
}
