package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LongArrayType implements IType<long[]> {
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
        return Types.LONG_ARRAY;
    }

    @Override
    public void write(ObjectOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (long l : obj) {
            stream.writeLong(l);
        }
    }

    public static LongArrayType read(ObjectInputStream stream) throws IOException {
        int len = stream.readInt();
        long[] arr = new long[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readLong();
        }
        return new LongArrayType(arr);
    }
}
