package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class FloatArrayType implements IType<float[]> {
    private float[] obj;

    public FloatArrayType(float[] obj) {
        this.obj = obj;
    }

    @Override
    public float[] getValue() {
        return obj;
    }

    @Override
    public void setValue(float[] obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.FLOAT_ARRAY;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeInt(obj.length);
        for (float i : obj) {
            stream.writeFloat(i);
        }
    }

    public static FloatArrayType read(DataInputStream stream) throws IOException {
        int len = stream.readInt();
        float[] arr = new float[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readFloat();
        }
        return new FloatArrayType(arr);
    }
}
