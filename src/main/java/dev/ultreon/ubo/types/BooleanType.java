package dev.ultreon.ubo.types;

import dev.ultreon.ubo.Types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BooleanType implements IType<Boolean> {
    private boolean obj;

    public BooleanType(boolean obj) {
        this.obj = obj;
    }

    @Override
    public Boolean getValue() {
        return obj;
    }

    @Override
    public void setValue(Boolean obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.BOOLEAN;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeBoolean(obj);
    }

    public static BooleanType read(DataInput input) throws IOException {
        return new BooleanType(input.readBoolean());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BooleanType)) return false;
        BooleanType that = (BooleanType) other;
        return obj == that.obj;
    }

    @Override
    public int hashCode() {
        return obj ? 1231 : 1237;
    }

    @Override
    public BooleanType copy() {
        return new BooleanType(obj);
    }

    @Override
    public String writeUso() {
        return obj ? "true" : "false";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
