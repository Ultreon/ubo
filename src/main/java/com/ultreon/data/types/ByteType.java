package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ByteType)) return false;
        ByteType byteType = (ByteType) other;
        return obj == byteType.obj;
    }

    @Override
    public int hashCode() {
        return obj;
    }

    @Override
    public ByteType copy() {
        return new ByteType(obj);
    }
}
