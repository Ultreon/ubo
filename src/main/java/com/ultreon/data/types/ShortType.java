package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ShortType implements IType<Short> {
    private short obj;

    public ShortType(short obj) {
        this.obj = obj;
    }

    @Override
    public Short getValue() {
        return obj;
    }

    @Override
    public void setValue(Short obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.SHORT;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeShort(obj);
    }

    public static ShortType read(DataInputStream stream) throws IOException {
        return new ShortType(stream.readShort());
    }
}
