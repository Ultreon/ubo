package com.ultreon.data;

import com.ultreon.data.types.IType;

import java.io.IOException;
import java.io.DataInputStream;

@FunctionalInterface
public interface IReader<T extends IType<?>> {
    T read(DataInputStream stream) throws IOException;
}
