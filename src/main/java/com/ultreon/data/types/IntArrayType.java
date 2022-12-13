package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class IntArrayType implements IType<int[]> {
    private int[] obj;

    public IntArrayType(int[] obj) {
        this.obj = obj;
    }

    @Override
    public int[] getValue() {
        return obj;
    }

    @Override
    public void setValue(int[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.INT_ARRAY;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (int i : obj) {
            stream.writeInt(i);
        }
    }

    public static IntArrayType read(DataInputStream stream) throws IOException {
        int len = stream.readInt();
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readInt();
        }
        return new IntArrayType(arr);
    }
}
