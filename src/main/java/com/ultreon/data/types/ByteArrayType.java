package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

public class ByteArrayType implements IType<byte[]> {
    private byte[] obj;

    public ByteArrayType(byte[] obj) {
        this.obj = obj;
    }

    @Override
    public byte[] getValue() {
        return obj;
    }

    @Override
    public void setValue(byte[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.BYTE_ARRAY;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (byte b : obj) {
            stream.writeByte(b);
        }
    }

    public static ByteArrayType read(DataInputStream stream) throws IOException {
        int len = stream.readInt();
        byte[] arr = new byte[len];
        for (int  i = 0; i < len; i++) {
            arr[i] = stream.readByte();
        }
        return new ByteArrayType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ByteArrayType)) return false;
        ByteArrayType that = (ByteArrayType) other;
        return Arrays.equals(obj, that.obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(obj);
    }

    @Override
    public ByteArrayType copy() {
        return new ByteArrayType(obj.clone());
    }

    public int size() {
        return obj.length;
    }
}
