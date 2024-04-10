package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class IntArrayType implements IType<int[]> {
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
        return Types.INT_ARRAY;
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

    public int size() {
        return obj.length;
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
}
