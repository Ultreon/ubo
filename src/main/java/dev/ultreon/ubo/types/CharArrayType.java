
package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class CharArrayType implements DataType<char[]> {
    private char[] obj;

    public CharArrayType(char[] obj) {
        this.obj = obj;
    }

    @Override
    public char[] getValue() {
        return obj;
    }

    @Override
    public void setValue(char[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return DataTypes.CHAR_ARRAY;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(obj.length);
        for (char i : obj) {
            output.writeChar(i);
        }
    }

    public static CharArrayType read(DataInput input) throws IOException {
        int len = input.readInt();
        char[] arr = new char[len];
        for (int i = 0; i < len; i++) {
            arr[i] = input.readChar();
        }
        return new CharArrayType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CharArrayType)) return false;
        CharArrayType that = (CharArrayType) other;
        return Arrays.equals(obj, that.obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(obj);
    }

    @Override
    public CharArrayType copy() {
        return new CharArrayType(obj.clone());
    }

    @Override
    public String writeUso() {
        StringBuilder builder = new StringBuilder("(c;");
        for (char v : obj) {
            builder.append("'").append(v == '\'' ? "\\'" : v).append("'").append(",");
        }

        return builder.substring(0, builder.length() - 1) + ")";
    }

    public int size() {
        return obj.length;
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
