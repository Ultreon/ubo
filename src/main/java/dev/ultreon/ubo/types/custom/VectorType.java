package dev.ultreon.ubo.types.custom;

public interface VectorType<V> extends Iterable<V> {
    int size();

    void fill(V value);

    V get(int index);

    void set(int index, V value);
}
