package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
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
    public void write(DataOutput output) throws IOException {
        output.writeInt(obj.length);
        for (int i : obj) {
            output.writeShort(i);
        }
    }

    public static ShortArrayType read(DataInput input) throws IOException {
        int len = input.readInt();
        short[] arr = new short[len];
        for (int i = 0; i < len; i++) {
            arr[i] = input.readShort();
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

    public int size() {
        return obj.length;
    }

    @Override
    public String writeUso() {
        StringBuilder builder = new StringBuilder("(s;");
        for (short v : obj) {
            builder.append(v).append(",");
        }

        return builder.substring(0, builder.length() - 1) + ")";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
