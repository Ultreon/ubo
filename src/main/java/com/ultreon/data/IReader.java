package com.ultreon.data;

import com.ultreon.data.types.IType;

import java.io.IOException;
import java.io.ObjectInputStream;

@FunctionalInterface
public interface IReader<T extends IType<?>> {
    T read(ObjectInputStream stream) throws IOException;
}
