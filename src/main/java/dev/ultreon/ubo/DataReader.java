package dev.ultreon.ubo;

import dev.ultreon.ubo.types.DataType;

import java.io.DataInput;
import java.io.IOException;

@FunctionalInterface
public interface DataReader<T extends DataType<?>> {
    T read(DataInput input) throws IOException;
}
