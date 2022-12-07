package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        return Types.CHAR;
    }

    @Override
    public void write(ObjectOutputStream stream) throws IOException {
        stream.writeUTF(obj);
    }

    public static StringType read(ObjectInputStream stream) throws IOException {
        return new StringType(stream.readUTF());
    }
}
