
package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class CharArrayType implements ArrayType<char[], Character> {
    private char[] obj;

    public CharArrayType(char[] obj) {
        this.obj = obj;
    }

    public CharArrayType(int length) {
        this.obj = new char[length];
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

    @Override
    public int size() {
        return obj.length;
    }

    @Override
    public @NotNull Character get(int index) {
        return obj[index];
    }

    @Override
    public void set(int index, Character value) {
        obj[index] = value;
    }

    public char getChar(int index) {
        return obj[index];
    }

    public void set(int index, char value) {
        obj[index] = value;
    }

    @Override
    public String toString() {
        return writeUso();
    }

    @Override
    public @NotNull Iterator<Character> iterator() {
        return new CharIterator(obj);
    }

    public static class CharIterator implements Iterator<Character> {
        private final char[] obj;
        private int index;

        public CharIterator(char[] obj) {
            this.obj = obj;
        }

        @Override
        public boolean hasNext() {
            return index < obj.length;
        }

        @Override
        public Character next() {
            return obj[index++];
        }

        public char nextChar() {
            return obj[index++];
        }
    }
}
