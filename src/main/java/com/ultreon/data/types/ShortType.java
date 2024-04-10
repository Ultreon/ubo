package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;

public class ShortType implements IType<Short> {
    private short obj;

    public ShortType(short obj) {
        this.obj = obj;
    }

    public ShortType(int obj) {
        this.obj = (short) obj;
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
    public void write(DataOutput output) throws IOException {
        output.writeShort(obj);
    }

    public static ShortType read(DataInput input) throws IOException {
        return new ShortType(input.readShort());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ShortType)) return false;
        ShortType shortType = (ShortType) other;
        return obj == shortType.obj;
    }

    @Override
    public int hashCode() {
        return obj;
    }

    @Override
    public ShortType copy() {
        return new ShortType(obj);
    }

    @Override
    public String writeUso() {
        return obj + "s";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
