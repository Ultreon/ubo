package dev.ultreon.ubo.util;

import dev.ultreon.ubo.DataIo;
import dev.ultreon.ubo.types.DataType;

import java.io.IOException;

public interface StringVisitor<T> {
    StringVisitor<DataType<?>> FROM_USO = DataIo::fromUso;

    T visit(String value) throws IOException;
}
