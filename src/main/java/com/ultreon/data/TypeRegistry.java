package com.ultreon.data;

import com.ultreon.data.types.*;

import java.io.IOException;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

public class TypeRegistry {
    private static final Map<Integer, IReader<? extends IType<?>>> READERS = new HashMap<>();
    private static final Map<Integer, Class<? extends IType<?>>> TYPES = new HashMap<>();
    private static final Map<String, Integer> ID_MAP = new HashMap<>();

    static {
        register(Types.BYTE, ByteType::read);
        register(Types.SHORT, ShortType::read);
        register(Types.INT, IntType::read);
        register(Types.LONG, LongType::read);
        register(Types.BIG_INT, BigIntType::read);
        register(Types.FLOAT, FloatType::read);
        register(Types.DOUBLE, DoubleType::read);
        register(Types.BIG_DEC, BigDecType::read);
        register(Types.CHAR, CharType::read);
        register(Types.BOOLEAN, BooleanType::read);
        register(Types.STRING, StringType::read);
        register(Types.LIST, ListType::read);
        register(Types.MAP, MapType::read);
        register(Types.BYTE_ARRAY, ByteArrayType::read);
        register(Types.SHORT_ARRAY, ShortArrayType::read);
        register(Types.INT_ARRAY, IntArrayType::read);
        register(Types.LONG_ARRAY, LongArrayType::read);
        register(Types.FLOAT_ARRAY, FloatArrayType::read);
        register(Types.DOUBLE_ARRAY, DoubleArrayType::read);
        register(Types.UUID, UUIDType::read);
        register(Types.BIT_SET, BitSetType::read);
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T extends IType<?>> void register(int id, IReader<T> reader, T... type) {
        Class<? extends T> componentType = (Class<? extends T>) type.getClass().getComponentType();
        READERS.put(id, reader);
        TYPES.put(id, componentType);
        ID_MAP.put(componentType.getName(), id);
    }

    public static IType<?> read(int id, DataInputStream stream) throws IOException {
        return READERS.get(id).read(stream);
    }

    public static Class<? extends IType<?>> getType(int id) {
        return TYPES.get(id);
    }

    public static int getId(Class<?> componentType) {
        return ID_MAP.get(componentType.getName());
    }
}
