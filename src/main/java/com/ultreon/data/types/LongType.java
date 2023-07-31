package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Objects;

public class LongType implements IType<Long> {
    private long obj;

    public LongType(long obj) {
        this.obj = obj;
    }

    @Override
    public Long getValue() {
        return obj;
    }

    @Override
    public void setValue(Long obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.LONG;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeLong(obj);
    }

    public static LongType read(DataInputStream stream) throws IOException {
        return new LongType(stream.readLong());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof LongType)) return false;
        LongType longType = (LongType) other;
        return obj == longType.obj;
    }

    @Override
    public int hashCode() {
        return Objects.hash(obj);
    }
}
