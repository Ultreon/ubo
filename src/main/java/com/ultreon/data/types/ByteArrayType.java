package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteArrayType implements IType<byte[]> {
    private byte[] obj;

    public ByteArrayType(byte[] obj) {
        this.obj = obj;
    }

    public ByteArrayType(ByteBuffer buffer) {
        this.obj = new byte[buffer.remaining()];
        buffer.get(obj);
    }

    public ByteArrayType(String str) {
        this.obj = str.getBytes(StandardCharsets.UTF_8);
    }

    public ByteArrayType(String str, Charset charset) {
        this.obj = str.getBytes(charset);
    }

    public ByteArrayType(int len) {
        this.obj = new byte[len];
    }

    public ByteArrayType(byte[] obj, int len) {
        this.obj = Arrays.copyOf(obj, len);
    }

    public ByteArrayType(Byte[] array) {
        this.obj = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            this.obj[i] = array[i];
        }
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
        for (int i = 0; i < len; i++) {
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

    @Override
    public String writeUso() {
        StringBuilder builder = new StringBuilder("(b;");
        for (byte v : obj) {
            builder.append(v).append(",");
        }

        return builder.substring(0, builder.length() - 1) + ")";
    }

    public int size() {
        return obj.length;
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
