package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ByteType implements IType<Byte> {
    private byte obj;

    public ByteType(byte obj) {
        this.obj = obj;
    }

    @Override
    public Byte getValue() {
        return obj;
    }

    @Override
    public void setValue(Byte obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.BYTE;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(obj);
    }

    public static ByteType read(DataInputStream stream) throws IOException {
        return new ByteType(stream.readByte());
    }
}
