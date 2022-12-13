package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

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
}
