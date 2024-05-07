package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntType implements DataType<Integer> {
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
        return DataTypes.INT;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(obj);
    }

    public static IntType read(DataInput input) throws IOException {
        return new IntType(input.readInt());
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
