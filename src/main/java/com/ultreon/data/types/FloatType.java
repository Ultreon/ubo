package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FloatType implements IType<Float> {
    private float obj;

    public FloatType(float obj) {
        this.obj = obj;
    }

    @Override
    public Float getValue() {
        return obj;
    }

    @Override
    public void setValue(Float obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.FLOAT;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeFloat(obj);
    }

    public static FloatType read(DataInputStream stream) throws IOException {
        return new FloatType(stream.readFloat());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof FloatType)) return false;
        FloatType floatType = (FloatType) other;
        return Float.compare(obj, floatType.obj) == 0;
    }

    @Override
    public int hashCode() {
        return Float.hashCode(obj);
    }

    @Override
    public FloatType copy() {
        return new FloatType(obj);
    }
}
