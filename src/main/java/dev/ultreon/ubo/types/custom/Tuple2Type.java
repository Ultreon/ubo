package dev.ultreon.ubo.types.custom;

import dev.ultreon.ubo.types.DataType;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Tuple2Type<T, A, B> extends DataType<T> {
    A getA();
    B getB();

    void setA(A a);
    void setB(B b);

    default <R> R map(BiFunction<A, B, R> mapper) {
        return mapper.apply(getA(), getB());
    }

    default <R> R mapA(Function<A, R> mapper) {
        return mapper.apply(getA());
    }

    default <R> R mapB(Function<B, R> mapper) {
        return mapper.apply(getB());
    }
}
