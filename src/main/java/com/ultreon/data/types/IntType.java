package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
    public void write(DataOutputStream stream) throws IOException {
        stream.writeInt(obj);
    }

    public static IntType read(DataInputStream stream) throws IOException {
        return new IntType(stream.readInt());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof IntType)) return false;
        IntType intType = (IntType) other;
        return obj == intType.obj;
    }

    @Override
    public int hashCode() {
        return obj;
    }

    @Override
    public IntType copy() {
        return new IntType(obj);
    }

    @Override
    public String writeUso() {
        return obj + "i";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
