package com.ultreon.data.types;

import java.io.IOException;
import java.io.DataOutputStream;

public interface IType<T> {
    T getValue();
    void setValue(T obj);
    int id();
    void write(DataOutputStream stream) throws IOException;

    boolean equals(Object other);

    int hashCode();
}
