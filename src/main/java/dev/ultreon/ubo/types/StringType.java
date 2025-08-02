package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringType implements DataType<String>, CharSequence {
    private String obj;

    public StringType(String obj) {
        this.obj = obj;
    }

    public StringType(StringBuilder obj) {
        this.obj = obj.toString();
    }

    public StringType(byte[] obj) {
        this.obj = new String(obj, StandardCharsets.UTF_8);
    }

    public StringType(byte[] obj, Charset charset) {
        this.obj = new String(obj, charset);
    }

    public StringType(char[] obj) {
        this.obj = new String(obj);
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
        return DataTypes.STRING;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        if (obj.length() > 65535) throw new IllegalArgumentException("String is too big to be written (length > 65535)");
        byte[] bytes = obj.getBytes(StandardCharsets.UTF_8);
        output.writeShort(bytes.length);
        for (byte aByte : bytes) {
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
    public int length() {
        return obj.length();
    }

    @Override
    public char charAt(int index) {
        return obj.charAt(index);
    }

    @NotNull
    @Override
    public CharSequence subSequence(int start, int end) {
        return obj.subSequence(start, end);
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
