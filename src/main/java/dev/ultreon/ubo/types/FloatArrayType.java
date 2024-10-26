package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class FloatArrayType implements ArrayType<float[], Float> {
    private float[] obj;

    public FloatArrayType(float[] obj) {
        this.obj = obj;
    }

    public FloatArrayType(int size) {
        this.obj = new float[size];
    }

    @Override
    public float[] getValue() {
        return obj;
    }

    @Override
    public void setValue(float[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return DataTypes.FLOAT_ARRAY;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(obj.length);
        for (float i : obj) {
            output.writeFloat(i);
        }
    }

    public static FloatArrayType read(DataInput input) throws IOException {
        int len = input.readInt();
        float[] arr = new float[len];
        for (int i = 0; i < len; i++) {
            arr[i] = input.readFloat();
        }
        return new FloatArrayType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof FloatArrayType)) return false;
        FloatArrayType that = (FloatArrayType) other;
        return Arrays.equals(obj, that.obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(obj);
    }

    @Override
    public FloatArrayType copy() {
        return new FloatArrayType(obj.clone());
    }

    @Override
    public int size() {
        return obj.length;
    }

    @Override
    public Float get(int index) {
        return obj[index];
    }

    @Override
    public void set(int index, Float value) {
        obj[index] = value;
    }

    @Override
    public String writeUso() {
        StringBuilder builder = new StringBuilder("(f;");
        for (float v : obj) {
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
    public @NotNull Iterator<Float> iterator() {
        return new FloatIterator(obj);
    }

    public static class FloatIterator implements Iterator<Float> {
        private final float[] obj;
        private int index;

        public FloatIterator(float[] obj) {
            this.obj = obj;
        }

        @Override
        public boolean hasNext() {
            return index < obj.length;
        }

        @Override
        @Deprecated
        public Float next() {
            return obj[index++];
        }

        public float nextFloat() {
            return obj[index++];
        }
    }
}
