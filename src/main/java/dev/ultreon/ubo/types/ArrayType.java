package dev.ultreon.ubo.types;

import org.jetbrains.annotations.NotNull;

public interface ArrayType<T> extends DataType<T> {
    int size();
}
