package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
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
    public void write(DataOutput output) throws IOException {
        output.writeInt(obj.length);
        for (double i : obj) {
            output.writeDouble(i);
        }
    }

    public static DoubleArrayType read(DataInput input) throws IOException {
        int len = input.readInt();
        double[] arr = new double[len];
        for (int i = 0; i < len; i++) {
            arr[i] = input.readDouble();
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

    @Override
    public String writeUso() {
        StringBuilder builder = new StringBuilder("(d;");
        for (double v : obj) {
            builder.append(v).append(",");
        }

        if (obj.length > 0) {
            return builder.substring(0, builder.length() - 1) + ")";
        }

        return builder.append(")").toString();
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
