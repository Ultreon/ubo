package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
    public void write(ObjectOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (double i : obj) {
            stream.writeDouble(i);
        }
    }

    public static DoubleArrayType read(ObjectInputStream stream) throws IOException {
        int len = stream.readInt();
        double[] arr = new double[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readDouble();
        }
        return new DoubleArrayType(arr);
    }
}
