package com.ultreon.data.util;

import com.ultreon.data.DataIo;
import com.ultreon.data.types.IType;

import java.io.IOException;

public interface StringVisitor<T> {
    StringVisitor<IType<?>> FROM_USO = DataIo::fromUso;

    T visit(String value) throws IOException;
}
