package com.ultreon.data.types;

import com.ultreon.data.util.DataTypeVisitor;

import java.io.DataOutput;
import java.io.IOException;

public interface IType<T> {
    T getValue();

    void setValue(T obj);

    int id();

    void write(DataOutput output) throws IOException;

    boolean equals(Object other);

    int hashCode();

    IType<T> copy();

    String writeUso();

    default <R> R accept(DataTypeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
