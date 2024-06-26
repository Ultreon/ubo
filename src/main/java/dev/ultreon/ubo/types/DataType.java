package dev.ultreon.ubo.types;

import dev.ultreon.ubo.util.DataTypeVisitor;

import java.io.DataOutput;
import java.io.IOException;

public interface DataType<T> {
    T getValue();

    void setValue(T obj);

    int id();

    void write(DataOutput output) throws IOException;

    boolean equals(Object other);

    int hashCode();

    DataType<T> copy();

    String writeUso();

    default <R> R accept(DataTypeVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
