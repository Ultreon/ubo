package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class UUIDType implements IType<UUID> {
    private UUID obj;

    public UUIDType(UUID obj) {
        this.obj = obj;
    }

    @Override
    public UUID getValue() {
        return obj;
    }

    @Override
    public void setValue(UUID obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.UUID;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeLong(obj.getMostSignificantBits());
        stream.writeLong(obj.getLeastSignificantBits());
    }

    public static UUIDType read(DataInputStream stream) throws IOException {
        long msb = stream.readLong();
        long lsb = stream.readLong();
        return new UUIDType(new UUID(msb, lsb));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof UUIDType)) return false;
        UUIDType uuidType = (UUIDType) other;
        return Objects.equals(obj, uuidType.obj);
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }

    @Override
    public UUIDType copy() {
        return new UUIDType(new UUID(obj.getMostSignificantBits(), obj.getLeastSignificantBits()));
    }

    @Override
    public String writeUso() {
        return '<' + obj.toString() + '>';
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
