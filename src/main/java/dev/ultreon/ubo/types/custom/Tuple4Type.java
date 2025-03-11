package dev.ultreon.ubo.types.custom;

import dev.ultreon.ubo.types.DataType;

import java.util.function.Function;

public interface Tuple4Type<T, A, B, C, D> extends DataType<T> {
    A getA();
    B getB();
    C getC();
    D getD();

    void setA(A a);
    void setB(B b);
    void setC(C c);
    void setD(D d);

    default <R> R mapA(Function<A, R> mapper) {
        return mapper.apply(getA());
    }
    default <R> R mapB(Function<B, R> mapper) {
        return mapper.apply(getB());
    }
    default <R> R mapC(Function<C, R> mapper) {
        return mapper.apply(getC());
    }
    default <R> R mapD(Function<D, R> mapper) {
        return mapper.apply(getD());
    }
}
