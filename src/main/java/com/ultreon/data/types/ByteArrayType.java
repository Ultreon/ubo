package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
    public void write(ObjectOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (byte b : obj) {
            stream.writeByte(b);
        }
    }

    public static ByteArrayType read(ObjectInputStream stream) throws IOException {
        int len = stream.readInt();
        byte[] arr = new byte[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readByte();
        }
        return new ByteArrayType(arr);
    }
}
