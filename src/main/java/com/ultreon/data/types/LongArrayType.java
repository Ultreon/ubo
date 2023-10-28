package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LongArrayType implements IType<long[]> {
    private long[] obj;

    public LongArrayType(long[] obj) {
        this.obj = obj;
    }

    @Override
    public long[] getValue() {
        return obj;
    }

    @Override
    public void setValue(long[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.LONG_ARRAY;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (long l : obj) {
            stream.writeLong(l);
        }
    }

    public static LongArrayType read(DataInputStream stream) throws IOException {
        int len = stream.readInt();
        long[] arr = new long[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readLong();
        }
        return new LongArrayType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof LongArrayType)) return false;
        LongArrayType that = (LongArrayType) other;
        return Arrays.equals(obj, that.obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(obj);
    }

    @Override
    public LongArrayType copy() {
        return new LongArrayType(obj.clone());
    }

    public int size() {
        return obj.length;
    }
}
