package dev.ultreon.ubo.types;

public interface ArrayType<T, B> extends DataType<T>, Iterable<B> {
    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    B get(int index);

    void set(int index, B value);

    default void fill(B value) {
        for (int i = 0; i < size(); i++) {
            set(i, value);
        }
    }
}
