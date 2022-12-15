package com.ultreon.data.types;

import com.ultreon.data.TypeRegistry;
import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

public class MapType implements IType<Map<String, IType<?>>> {
    private Map<String, IType<?>> obj;

    public MapType() {
        obj = new HashMap<>();
    }

    public MapType(Map<String, IType<?>> list) {
        setValue(list);
    }

    @Override
    public Map<String, IType<?>> getValue() {
        return obj;
    }

    @Override
    public void setValue(Map<String, IType<?>> obj) {
        this.obj = obj;
    }
    
    public Set<String> keys() {
        return obj.keySet();
    }

    public Set<Entry<String, IType<?>>> entries() {
        return obj.entrySet();
    }

    public Collection<IType<?>> values() {
        return obj.values();
    }

    @Override
    public int id() {
        return Types.MAP;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeInt(obj.size());
        for (var e : obj.entrySet()) {
            stream.writeShort(e.getKey().length());
            for (byte aByte : e.getKey().getBytes(StandardCharsets.UTF_8)) {
                stream.writeByte(aByte);
            }
            var value = e.getValue();
            stream.writeByte(value.id());
            value.write(stream);
        }
    }

    public static MapType read(DataInputStream stream) throws IOException {
        int len = stream.readInt();
        Map<String, IType<?>> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            short strLen = stream.readShort();
            byte[] bytes = new byte[strLen];
            for (int j = 0; j < strLen; j++) {
                bytes[j] = stream.readByte();
            }
            String key = new String(bytes, StandardCharsets.UTF_8);
            var id = stream.readByte();
            map.put(key, TypeRegistry.read(id, stream));
        }
        
        return new MapType(map);
    }

    public void put(String key, IType<?> type) {
        obj.put(key, type);
    }

    public void putByte(String key, byte value) {
        put(key, new ByteType(value));
    }

    public void putByte(String key, int value) {
        put(key, new ByteType((byte) value));
    }

    public void putShort(String key, short value) {
        put(key, new ShortType(value));
    }

    public void putShort(String key, int value) {
        put(key, new ShortType((short) value));
    }

    public void putInt(String key, int value) {
        put(key, new IntType(value));
    }

    public void putLong(String key, long value) {
        put(key, new LongType(value));
    }

    public void putBigInt(String key, BigInteger value) {
        put(key, new BigIntType(value));
    }

    public void putFloat(String key, float value) {
        put(key, new FloatType(value));
    }

    public void putDouble(String key, double value) {
        put(key, new DoubleType(value));
    }

    public void putBigDec(String key, BigDecimal value) {
        put(key, new BigDecType(value));
    }
    
    public void putChar(String key, char value) {
        put(key, new CharType(value));
    }

    public void putBoolean(String key, boolean value) {
        put(key, new BooleanType(value));
    }

    public void putString(String key, String value) {
        put(key, new StringType(value));
    }

    public void putByteArray(String key, byte[] value) {
        put(key, new ByteArrayType(value));
    }

    public void putShortArray(String key, short[] value) {
        put(key, new ShortArrayType(value));
    }

    public void putIntArray(String key, int[] value) {
        put(key, new IntArrayType(value));
    }

    public void putLongArray(String key, long[] value) {
        put(key, new LongArrayType(value));
    }

    public void putFloatArray(String key, float[] value) {
        put(key, new FloatArrayType(value));
    }

    public void putDoubleArray(String key, double[] value) {
        put(key, new DoubleArrayType(value));
    }

    public void putUUID(String key, UUID value) {
        put(key, new UUIDType(value));
    }

    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    public byte getByte(String key, byte def) {
        IType<?> iType = get(key);
        if (iType instanceof ByteType obj) {
            return obj.getValue();
        }
        return def;
    }

    public short getShort(String key) {
        return getShort(key, (byte) 0);
    }

    public short getShort(String key, short def) {
        IType<?> iType = get(key);
        if (iType instanceof ShortType obj) {
            return obj.getValue();
        }
        return def;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int def) {
        IType<?> iType = get(key);
        if (iType instanceof IntType obj) {
            return obj.getValue();
        }
        return def;
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long def) {
        IType<?> iType = get(key);
        if (iType instanceof LongType obj) {
            return obj.getValue();
        }
        return def;
    }

    public BigInteger getBigInt(String key) {
        return getBigInt(key, BigInteger.ZERO);
    }

    public BigInteger getBigInt(String key, BigInteger def) {
        IType<?> iType = get(key);
        if (iType instanceof BigIntType obj) {
            return obj.getValue();
        }
        return def;
    }

    public float getFloat(String key) {
        return getFloat(key, (float) 0);
    }

    public float getFloat(String key, float def) {
        IType<?> iType = get(key);
        if (iType instanceof FloatType obj) {
            return obj.getValue();
        }
        return def;
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double def) {
        IType<?> iType = get(key);
        if (iType instanceof DoubleType obj) {
            return obj.getValue();
        }
        return def;
    }

    public BigDecimal getBigDec(String key) {
        return getBigDec(key, BigDecimal.ZERO);
    }

    public BigDecimal getBigDec(String key, BigDecimal def) {
        IType<?> iType = get(key);
        if (iType instanceof BigDecType obj) {
            return obj.getValue();
        }
        return def;
    }

    public char getChar(String key) {
        return getChar(key, (char) 0);
    }

    public char getChar(String key, char def) {
        IType<?> iType = get(key);
        if (iType instanceof CharType obj) {
            return obj.getValue();
        }
        return def;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        IType<?> iType = get(key);
        if (iType instanceof BooleanType obj) {
            return obj.getValue();
        }
        return def;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String def) {
        IType<?> iType = get(key);
        if (iType instanceof StringType obj) {
            return obj.getValue();
        }
        return def;
    }

    public byte[] getByteArray(String key) {
        return getByteArray(key, null);
    }

    public byte[] getByteArray(String key, byte[] def) {
        IType<?> iType = get(key);
        if (iType instanceof ByteArrayType obj) {
            return obj.getValue();
        }
        return def;
    }

    public short[] getShortArray(String key) {
        return getShortArray(key, null);
    }

    public short[] getShortArray(String key, short[] def) {
        IType<?> iType = get(key);
        if (iType instanceof ShortArrayType obj) {
            return obj.getValue();
        }
        return def;
    }

    public int[] getIntArray(String key) {
        return getIntArray(key, null);
    }

    public int[] getIntArray(String key, int[] def) {
        IType<?> iType = get(key);
        if (iType instanceof IntArrayType obj) {
            return obj.getValue();
        }
        return def;
    }

    public long[] getLongArray(String key) {
        return getLongArray(key, null);
    }

    public long[] getLongArray(String key, long[] def) {
        IType<?> iType = get(key);
        if (iType instanceof LongArrayType obj) {
            return obj.getValue();
        }
        return def;
    }

    public float[] getFloatArray(String key) {
        return getFloatArray(key, null);
    }

    public float[] getFloatArray(String key, float[] def) {
        IType<?> iType = get(key);
        if (iType instanceof FloatArrayType obj) {
            return obj.getValue();
        }
        return def;
    }

    public double[] getDoubleArray(String key) {
        return getDoubleArray(key, null);
    }

    public double[] getDoubleArray(String key, double[] def) {
        IType<?> iType = get(key);
        if (iType instanceof DoubleArrayType obj) {
            return obj.getValue();
        }
        return def;
    }

    public MapType getMap(String key) {
        return getMap(key, null);
    }

    public MapType getMap(String key, MapType def) {
        IType<?> iType = get(key);
        if (iType instanceof MapType obj) {
            return obj;
        }
        return def;
    }
    public ListType getList(String key, int id) {
        return getList(key, new ListType(id));
    }

    public ListType getList(String key, ListType def) {
        IType<?> iType = get(key);
        if (iType instanceof ListType obj) {
            if (obj.type() != def.type()) {
                return def;
            }
            return obj;
        }
        return def;
    }

    public UUID getUUID(String key) {
        return getUUID(key, null);
    }

    public UUID getUUID(String key, UUID def) {
        IType<?> iType = get(key);
        if (iType instanceof UUIDType obj) {
            return obj.getValue();
        }
        return def;
    }

    public IType<?> get(String key) {
        return obj.get(key);
    }
}
