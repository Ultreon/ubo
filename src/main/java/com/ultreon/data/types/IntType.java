package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IntType implements IType<Integer> {
    private int obj;

    public IntType(int obj) {
        this.obj = obj;
    }

    @Override
    public Integer getValue() {
        return obj;
    }

    @Override
    public void setValue(Integer obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.INT;
    }

    @Override
    public void write(ObjectOutputStream stream) throws IOException {
        stream.writeInt(obj);
    }

    public static IntType read(ObjectInputStream stream) throws IOException {
        return new IntType(stream.readInt());
    }
}
