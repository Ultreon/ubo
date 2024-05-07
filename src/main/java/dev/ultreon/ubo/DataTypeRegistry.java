package dev.ultreon.ubo;

import dev.ultreon.ubo.types.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataTypeRegistry {
    private static final Map<Integer, DataReader<? extends DataType<?>>> READERS = new HashMap<>();
    private static final Map<Integer, Class<? extends DataType<?>>> TYPES = new HashMap<>();
    private static final Map<String, Integer> ID_MAP = new HashMap<>();

    static {
        register(DataTypes.BYTE, ByteType::read);
        register(DataTypes.SHORT, ShortType::read);
        register(DataTypes.INT, IntType::read);
        register(DataTypes.LONG, LongType::read);
        register(DataTypes.BIG_INT, BigIntType::read);
        register(DataTypes.FLOAT, FloatType::read);
        register(DataTypes.DOUBLE, DoubleType::read);
        register(DataTypes.BIG_DEC, BigDecType::read);
        register(DataTypes.CHAR, CharType::read);
        register(DataTypes.BOOLEAN, BooleanType::read);
        register(DataTypes.STRING, StringType::read);
        register(DataTypes.LIST, ListType::read);
        register(DataTypes.MAP, MapType::read);
        register(DataTypes.BYTE_ARRAY, ByteArrayType::read);
        register(DataTypes.SHORT_ARRAY, ShortArrayType::read);
        register(DataTypes.INT_ARRAY, IntArrayType::read);
        register(DataTypes.LONG_ARRAY, LongArrayType::read);
        register(DataTypes.FLOAT_ARRAY, FloatArrayType::read);
        register(DataTypes.DOUBLE_ARRAY, DoubleArrayType::read);
        register(DataTypes.CHAR_ARRAY, CharArrayType::read);
        register(DataTypes.UUID, UUIDType::read);
        register(DataTypes.BIT_SET, BitSetType::read);
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T extends DataType<?>> void register(int id, DataReader<T> reader, T... type) {
        Class<? extends T> componentType = (Class<? extends T>) type.getClass().getComponentType();
        READERS.put(id, reader);
        TYPES.put(id, componentType);
        ID_MAP.put(componentType.getName(), id);
    }

    public static DataType<?> read(int id, DataInput input) throws IOException {
        if (!READERS.containsKey(id))
            throw new DataTypeException("Unknown datatype id: " + id);

        return READERS.get(id).read(input);
    }

    public static Class<? extends DataType<?>> getType(int id) {
        return TYPES.get(id);
    }

    public static int getId(Class<?> componentType) {
        return ID_MAP.get(componentType.getName());
    }

    public static int getIdOrThrow(Class<?> componentType) {
        String name = componentType.getName();
        Integer id = ID_MAP.get(name);

        if (id == null)
            throw new IllegalArgumentException("No type registered for " + name);

        return id;
    }
}
