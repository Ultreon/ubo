package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ShortArrayType implements IType<short[]> {
    private short[] obj;

    public ShortArrayType(short[] obj) {
        this.obj = obj;
    }

    @Override
    public short[] getValue() {
        return obj;
    }

    @Override
    public void setValue(short[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.SHORT_ARRAY;
    }

    @Override
    public void write(ObjectOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (int i : obj) {
            stream.writeShort(i);
        }
    }

    public static ShortArrayType read(ObjectInputStream stream) throws IOException {
        int len = stream.readInt();
        short[] arr = new short[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readShort();
        }
        return new ShortArrayType(arr);
    }
}
