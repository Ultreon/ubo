package dev.ultreon.ubo.types.custom;

import dev.ultreon.ubo.types.DataType;

import java.util.function.Function;

public interface Tuple3Type<T, A, B, C> extends DataType<T> {
    A getA();
    B getB();
    C getC();

    void setA(A a);
    void setB(B b);
    void setC(C c);

    default <R> R mapA(Function<A, R> mapper) {
        return mapper.apply(getA());
    }

    default <R> R mapB(Function<B, R> mapper) {
        return mapper.apply(getB());
    }

    default <R> R mapC(Function<C, R> mapper) {
        return mapper.apply(getC());
    }
}
