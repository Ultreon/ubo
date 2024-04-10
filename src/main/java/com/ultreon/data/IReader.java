package com.ultreon.data;

import com.ultreon.data.types.IType;

import java.io.DataInput;
import java.io.IOException;

@FunctionalInterface
public interface IReader<T extends IType<?>> {
    T read(DataInput input) throws IOException;
}
