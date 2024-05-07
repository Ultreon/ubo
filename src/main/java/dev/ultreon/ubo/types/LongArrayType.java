package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class LongArrayType implements DataType<long[]> {
    private long[] obj;

    public LongArrayType(long[] obj) {
        this.obj = obj;
    }

    @Override
    public long[] getValue() {
        return obj;
    }

    @Override
    public void setValue(long[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return DataTypes.LONG_ARRAY;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(obj.length);
        for (long l : obj) {
            output.writeLong(l);
        }
    }

    public static LongArrayType read(DataInput input) throws IOException {
        int len = input.readInt();
        long[] arr = new long[len];
        for (int i = 0; i < len; i++) {
            arr[i] = input.readLong();
        }
        return new LongArrayType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof LongArrayType)) return false;
        LongArrayType that = (LongArrayType) other;
        return Arrays.equals(obj, that.obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(obj);
    }

    @Override
    public LongArrayType copy() {
        return new LongArrayType(obj.clone());
    }

    public int size() {
        return obj.length;
    }

    @Override
    public String writeUso() {
        StringBuilder builder = new StringBuilder("(l;");
        for (long v : obj) {
            builder.append(v).append(",");
        }

        return builder.substring(0, builder.length() - 1) + ")";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
