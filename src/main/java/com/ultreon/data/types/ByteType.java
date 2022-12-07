package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
    public void write(ObjectOutputStream stream) throws IOException {
        stream.writeByte(obj);
    }

    public static ByteType read(ObjectInputStream stream) throws IOException {
        return new ByteType(stream.readByte());
    }
}
