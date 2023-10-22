package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

public class ShortArrayType implements IType<short[]> {
    private short[] obj;

    public ShortArrayType(short[] obj) {
        this.obj = obj;
    }

    @Override
    public short[] getValue() {
        return obj;
    }

    @Override
    public void setValue(short[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.SHORT_ARRAY;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (int i : obj) {
            stream.writeShort(i);
        }
    }

    public static ShortArrayType read(DataInputStream stream) throws IOException {
        int len = stream.readInt();
        short[] arr = new short[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readShort();
        }
        return new ShortArrayType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ShortArrayType)) return false;
        ShortArrayType that = (ShortArrayType) other;
        return Arrays.equals(obj, that.obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(obj);
    }

    @Override
    public ShortArrayType copy() {
        return new ShortArrayType(obj.clone());
    }
}
