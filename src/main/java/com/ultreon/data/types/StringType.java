package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StringType implements IType<String> {
    private String obj;

    public StringType(String obj) {
        this.obj = obj;
    }

    @Override
    public String getValue() {
        return obj;
    }

    @Override
    public void setValue(String obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.STRING;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(obj.length());
        for (byte aByte : obj.getBytes(StandardCharsets.UTF_8)) {
            output.writeByte(aByte);
        }
    }

    public static StringType read(DataInput input) throws IOException {
        int strLen = input.readUnsignedShort();
        byte[] bytes = new byte[strLen];
        for (int j = 0; j < strLen; j++) {
            bytes[j] = input.readByte();
        }
        return new StringType(new String(bytes, StandardCharsets.UTF_8));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringType that = (StringType) o;
        return obj.equals(that.obj);
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }

    @Override
    public StringType copy() {
        return new StringType(obj);
    }

    @Override
    public String writeUso() {
        return "\"" + obj.replace("\"", "\\\"") + "\"";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
