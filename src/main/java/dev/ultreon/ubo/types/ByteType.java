package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteType implements DataType<Byte> {
    private byte obj;

    public ByteType(byte obj) {
        this.obj = obj;
    }

    public ByteType(int obj) {
        this.obj = (byte) obj;
    }

    @Override
    public Byte getValue() {
        return obj;
    }

    public byte getByteValue() {
        return obj;
    }

    @Override
    public void setValue(Byte obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    public void setValue(byte val) {
        this.obj = val;
    }

    @Override
    public int id() {
        return DataTypes.BYTE;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(obj);
    }

    public static ByteType read(DataInput input) throws IOException {
        return new ByteType(input.readByte());
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

    @Override
    public String writeUso() {
        return obj + "b";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
