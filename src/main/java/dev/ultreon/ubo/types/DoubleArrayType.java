package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class DoubleArrayType implements ArrayType<double[], Double> {
    private double[] obj;

    public DoubleArrayType(double[] obj) {
        this.obj = obj;
    }

    public DoubleArrayType(int size) {
        this.obj = new double[size];
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
        return DataTypes.DOUBLE_ARRAY;
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

    @Override
    public int size() {
        return obj.length;
    }

    @Override
    public Double get(int index) {
        return obj[index];
    }

    @Override
    public void set(int index, Double value) {
        obj[index] = value;
    }

    public double getDouble(int index) {
        return obj[index];
    }

    public void set(int index, double value) {
        obj[index] = value;
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

    @Override
    public @NotNull Iterator<Double> iterator() {
        return new DoubleIterator(obj);
    }

    public static class DoubleIterator implements Iterator<Double> {
        private final double[] obj;
        private int index;

        public DoubleIterator(double[] obj) {
            this.obj = obj;
        }

        @Override
        public boolean hasNext() {
            return index < obj.length;
        }

        @Override
        public Double next() {
            return obj[index++];
        }

        public double nextDouble() {
            return obj[index++];
        }
    }
}
