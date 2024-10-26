package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class IntArrayType implements ArrayType<int[], Integer> {
    private int[] obj;

    public IntArrayType(int[] obj) {
        this.obj = obj;
    }

    @Override
    public int[] getValue() {
        return obj;
    }

    @Override
    public void setValue(int[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return DataTypes.INT_ARRAY;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(obj.length);
        for (int i : obj) {
            output.writeInt(i);
        }
    }

    public static IntArrayType read(DataInput input) throws IOException {
        int len = input.readInt();
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = input.readInt();
        }
        return new IntArrayType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof IntArrayType)) return false;
        IntArrayType that = (IntArrayType) other;
        return Arrays.equals(obj, that.obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(obj);
    }

    @Override
    public IntArrayType copy() {
        return new IntArrayType(obj.clone());
    }

    @Override
    public int size() {
        return obj.length;
    }

    @Override
    public Integer get(int index) {
        return obj[index];
    }

    @Override
    public void set(int index, Integer value) {
        obj[index] = value;
    }

    public int getInt(int index) {
        return obj[index];
    }

    public void set(int index, int value) {
        obj[index] = value;
    }

    @Override
    public String writeUso() {
        StringBuilder builder = new StringBuilder("(i;");
        for (int v : obj) {
            builder.append(v).append(",");
        }

        return builder.substring(0, builder.length() - 1) + ")";
    }

    @Override
    public String toString() {
        return writeUso();
    }

    @Override
    public @NotNull IntIterator iterator() {
        return new IntIterator(obj);
    }

    public static class IntIterator implements Iterator<Integer> {
        private final int[] obj;
        private int index;

        public IntIterator(int[] obj) {
            this.obj = obj;
        }

        @Override
        public boolean hasNext() {
            return index < obj.length;
        }

        @Override
        public Integer next() {
            return obj[index++];
        }

        public int nextInt() {
            return obj[index++];
        }
    }
}
