package dev.ultreon.ubo.types;

import org.jetbrains.annotations.NotNull;

public interface ArrayType<T, B> extends DataType<T>, Iterable<B> {
    int size();

    B get(int index);

    void set(int index, B value);
}
