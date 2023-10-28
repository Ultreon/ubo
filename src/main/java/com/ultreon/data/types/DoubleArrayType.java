package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

public class DoubleArrayType implements IType<double[]> {
    private double[] obj;

    public DoubleArrayType(double[] obj) {
        this.obj = obj;
    }

    @Override
    public double[] getValue() {
        return obj;
    }

    @Override
    public void setValue(double[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.DOUBLE_ARRAY;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (double i : obj) {
            stream.writeDouble(i);
        }
    }

    public static DoubleArrayType read(DataInputStream stream) throws IOException {
        int len = stream.readInt();
        double[] arr = new double[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readDouble();
        }
        return new DoubleArrayType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof DoubleArrayType)) return false;
        DoubleArrayType that = (DoubleArrayType) other;
        return Arrays.equals(obj, that.obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(obj);
    }

    @Override
    public DoubleArrayType copy() {
        return new DoubleArrayType(obj.clone());
    }

    public int size() {
        return obj.length;
    }
}
