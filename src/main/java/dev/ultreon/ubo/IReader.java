package dev.ultreon.ubo;

import dev.ultreon.ubo.types.IType;

import java.io.DataInput;
import java.io.IOException;

@FunctionalInterface
public interface IReader<T extends IType<?>> {
    T read(DataInput input) throws IOException;
}
