package dev.ultreon.tests.data;

import dev.ultreon.ubo.types.*;
import dev.ultreon.ubo.types.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.Iterator;
import java.util.UUID;

public class TypeTests {
    @Test
    @DisplayName("ListTypes")
    void listTypes() {
        ListType<StringType> list = new ListType<>();
        list.add(new StringType("Apple"));
        list.add(new StringType("Banana"));
        list.add(new StringType("Pear"));
        list.add(new StringType("Orange"));
        list.add(new StringType("Watermelon"));
        Assertions.assertEquals(new StringType("Apple"), list.get(0));
        Assertions.assertEquals(new StringType("Banana"), list.get(1));
        Assertions.assertEquals(new StringType("Pear"), list.get(2));
        Assertions.assertEquals(new StringType("Orange"), list.get(3));
        Assertions.assertEquals(new StringType("Watermelon"), list.get(4));

        Assertions.assertEquals(5, list.size());

        Assertions.assertDoesNotThrow(() -> {
            for (StringType s : list) {
                Assertions.assertNotNull(s);
            }
        });

        list.remove(0);

        Assertions.assertEquals(4, list.size());

        Iterator<StringType> iterator = list.iterator();

        Assertions.assertEquals(new StringType("Banana"), iterator.next());
        Assertions.assertEquals(new StringType("Pear"), iterator.next());
        Assertions.assertEquals(new StringType("Orange"), iterator.next());
        Assertions.assertEquals(new StringType("Watermelon"), iterator.next());

        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("MapTypes")
    void mapTypes() {
        MapType map = new MapType();
        map.put("String", new StringType("Apple"));
        map.put("Integer", new IntType(5));
        map.put("List", new ListType<>(new StringType("Banana")));
        map.putInt("Integer2", 6);
        map.putString("String2", "Banana");
        map.putBigInt("Integer3", new BigInteger("1234567890123456789012345678901234567890"));
        map.putBigDec("Integer4", new BigDecimal("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890"));
        map.putByteArray("ByteArray", new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        map.putBoolean("Boolean", true);

        Assertions.assertEquals(new StringType("Apple"), map.get("String"));
        Assertions.assertEquals(new IntType(5), map.get("Integer"));
        Assertions.assertEquals(new ListType<>(new StringType("Banana")), map.get("List"));
        Assertions.assertEquals(6, map.getInt("Integer2"));
        Assertions.assertEquals("Banana", map.getString("String2"));
        Assertions.assertEquals(new BigInteger("1234567890123456789012345678901234567890"), map.getBigInt("Integer3"));
        Assertions.assertEquals(new BigDecimal("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890"), map.getBigDec("Integer4"));
        Assertions.assertArrayEquals(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, map.getByteArray("ByteArray"));
        Assertions.assertTrue(map.getBoolean("Boolean"));

        Assertions.assertEquals(9, map.size());

        map.remove("Integer2");

        Assertions.assertEquals(8, map.size());
    }

    @Test
    @DisplayName("PrimitiveTypes")
    void primitiveTypes() {
        Assertions.assertEquals(5, new IntType(5).getValue());
        Assertions.assertEquals("Apple", new StringType("Apple").getValue());
        Assertions.assertEquals(true, new BooleanType(true).getValue());
        Assertions.assertArrayEquals(new boolean[]{true, false, true, false, true, false, true, false, true, false}, new BooleanArrayType(new boolean[]{true, false, true, false, true, false, true, false, true, false}).getValue());
        Assertions.assertArrayEquals(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, new ByteArrayType(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}).getValue());
        Assertions.assertEquals(new BigInteger("1234567890123456789012345678901234567890"), new BigIntType(new BigInteger("1234567890123456789012345678901234567890")).getValue());

        Assertions.assertEquals("5i", new IntType(5).toString());
        Assertions.assertEquals("\"Apple\"", new StringType("Apple").toString());
        Assertions.assertEquals("true", new BooleanType(true).toString());
        Assertions.assertEquals("(z;true,true,false,true,false,true,true,false,true,false,false,true,false,false)", new BooleanArrayType(new boolean[]{true, false, true, false, true, false, true, false, true, false}).toString());
        Assertions.assertEquals("(b;0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)", new ByteArrayType(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}).toString());
        Assertions.assertEquals("1234567890123456789012345678901234567890I", new BigIntType(new BigInteger("1234567890123456789012345678901234567890")).toString());
        Assertions.assertEquals("5b", new ByteType(5).toString());
        Assertions.assertEquals("5s", new ShortType(5).toString());
        Assertions.assertEquals("5i", new IntType(5).toString());
        Assertions.assertEquals("5l", new LongType(5).toString());
        Assertions.assertEquals("5.5f", new FloatType(5.5f).toString());
        Assertions.assertEquals("5.5d", new DoubleType(5.5).toString());
        Assertions.assertEquals("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890D", new BigDecType(new BigDecimal("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890")).toString());
        Assertions.assertEquals("<00000000-0000-0000-0000-000000000000>", new UUIDType(UUID.fromString("00000000-0000-0000-0000-000000000000")).toString());
        Assertions.assertEquals("x;", new BitSetType(new BitSet()).toString());
        BitSet obj = new BitSet(5);
        obj.set(0);
        obj.set(3);
        obj.set(4);
        obj.set(9);
        Assertions.assertEquals("x1001100001;", new BitSetType(obj).toString());

        Assertions.assertEquals(new IntType(5), new IntType(5).copy());
        Assertions.assertEquals(new StringType("Apple"), new StringType("Apple").copy());
        Assertions.assertEquals(new BooleanType(true), new BooleanType(true).copy());
        Assertions.assertEquals(new BooleanArrayType(new boolean[]{true, true, false, true, false, true, true, false, true, false, false, true, false, false}), new BooleanArrayType(new boolean[]{true, false, true, false, true, false, true, false, true, false}).copy());
        Assertions.assertEquals(new ByteArrayType(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}), new ByteArrayType(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}).copy());
        Assertions.assertEquals(new BigIntType(new BigInteger("1234567890123456789012345678901234567890")), new BigIntType(new BigInteger("1234567890123456789012345678901234567890")).copy());
        Assertions.assertEquals(new ByteType(5), new ByteType(5).copy());
        Assertions.assertEquals(new ShortType(5), new ShortType(5).copy());
        Assertions.assertEquals(new IntType(5), new IntType(5).copy());
        Assertions.assertEquals(new LongType(5), new LongType(5).copy());
        Assertions.assertEquals(new FloatType(5.5f), new FloatType(5.5f).copy());
        Assertions.assertEquals(new DoubleType(5.5), new DoubleType(5.5).copy());
        Assertions.assertEquals(new BigDecType("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890"), new BigDecType("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890").copy());
        Assertions.assertEquals(new UUIDType(UUID.fromString("00000000-0000-0000-0000-000000000000")).copy(), new UUIDType(UUID.fromString("00000000-0000-0000-0000-000000000000")));
        Assertions.assertEquals(new BitSetType(new BitSet()), new BitSetType(new BitSet()).copy());
        Assertions.assertEquals(new BitSetType(new BitSet(5)), new BitSetType(new BitSet(5)).copy());
        BitSet obj2 = new BitSet(5);
        obj2.set(0);
        obj2.set(3);
        obj2.set(4);
        obj2.set(9);

        BitSet obj3 = new BitSet(5);
        obj3.set(0);
        obj3.set(3);
        obj3.set(4);
        obj3.set(9);

        Assertions.assertEquals(new BitSetType(obj2).copy(), new BitSetType(obj3));
    }
}
