package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LongType implements DataType<Long> {
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
        return DataTypes.LONG;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeLong(obj);
    }

    public static LongType read(DataInput input) throws IOException {
        return new LongType(input.readLong());
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
        return (int) (obj ^ obj >>> 32);
    }

    @Override
    public LongType copy() {
        return new LongType(obj);
    }

    @Override
    public String writeUso() {
        return obj + "l";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
