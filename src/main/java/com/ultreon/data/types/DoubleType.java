package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Objects;

public class DoubleType implements IType<Double> {
    private double obj;

    public DoubleType(double obj) {
        this.obj = obj;
    }

    @Override
    public Double getValue() {
        return obj;
    }

    @Override
    public void setValue(Double obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.DOUBLE;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeDouble(obj);
    }

    public static DoubleType read(DataInputStream stream) throws IOException {
        return new DoubleType(stream.readDouble());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof DoubleType)) return false;
        DoubleType that = (DoubleType) other;
        return Double.compare(obj, that.obj) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(obj);
    }

    @Override
    public DoubleType copy() {
        return new DoubleType(obj);
    }
}
