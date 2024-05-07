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
        Assertions.assertEquals(list.get(0), new StringType("Apple"));
        Assertions.assertEquals(list.get(1), new StringType("Banana"));
        Assertions.assertEquals(list.get(2), new StringType("Pear"));
        Assertions.assertEquals(list.get(3), new StringType("Orange"));
        Assertions.assertEquals(list.get(4), new StringType("Watermelon"));

        Assertions.assertEquals(list.size(), 5);

        Assertions.assertDoesNotThrow(() -> {
            for (StringType s : list) {
                Assertions.assertNotNull(s);
            }
        });

        list.remove(0);

        Assertions.assertEquals(list.size(), 4);

        Iterator<StringType> iterator = list.iterator();

        Assertions.assertEquals(iterator.next(), new StringType("Banana"));
        Assertions.assertEquals(iterator.next(), new StringType("Pear"));
        Assertions.assertEquals(iterator.next(), new StringType("Orange"));
        Assertions.assertEquals(iterator.next(), new StringType("Watermelon"));

        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("MapTypes")
    void mapTypes() {
        MapType map = new MapType();
        map.put("String", new StringType("Apple"));
        map.put("Integer", new IntType(5));
        map.put("List", new ListType<StringType>(new StringType("Banana")));
        map.putInt("Integer2", 6);
        map.putString("String2", "Banana");
        map.putBigInt("Integer3", new BigInteger("1234567890123456789012345678901234567890"));
        map.putBigDec("Integer4", new BigDecimal("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890"));
        map.putByteArray("ByteArray", new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        map.putBoolean("Boolean", true);

        Assertions.assertEquals(map.get("String"), new StringType("Apple"));
        Assertions.assertEquals(map.get("Integer"), new IntType(5));
        Assertions.assertEquals(map.get("List"), new ListType<StringType>(new StringType("Banana")));
        Assertions.assertEquals(map.getInt("Integer2"), 6);
        Assertions.assertEquals(map.getString("String2"), "Banana");
        Assertions.assertEquals(map.getBigInt("Integer3"), new BigInteger("1234567890123456789012345678901234567890"));
        Assertions.assertEquals(map.getBigDec("Integer4"), new BigDecimal("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890"));
        Assertions.assertArrayEquals(map.getByteArray("ByteArray"), new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        Assertions.assertEquals(map.getBoolean("Boolean"), true);

        Assertions.assertEquals(map.size(), 9);

        map.remove("Integer2");

        Assertions.assertEquals(map.size(), 8);
    }

    @Test
    @DisplayName("PrimitiveTypes")
    void primitiveTypes() {
        Assertions.assertEquals(new IntType(5).getValue(), 5);
        Assertions.assertEquals(new StringType("Apple").getValue(), "Apple");
        Assertions.assertEquals(new BooleanType(true).getValue(), true);
        Assertions.assertArrayEquals(new ByteArrayType(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}).getValue(), new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
        Assertions.assertEquals(new BigIntType(new BigInteger("1234567890123456789012345678901234567890")).getValue(), new BigInteger("1234567890123456789012345678901234567890"));

        Assertions.assertEquals(new IntType(5).toString(), "5i");
        Assertions.assertEquals(new StringType("Apple").toString(), "\"Apple\"");
        Assertions.assertEquals(new BooleanType(true).toString(), "true");
        Assertions.assertEquals(new ByteArrayType(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}).toString(), "(b;0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)");
        Assertions.assertEquals(new BigIntType(new BigInteger("1234567890123456789012345678901234567890")).toString(), "1234567890123456789012345678901234567890I");
        Assertions.assertEquals(new ByteType(5).toString(), "5b");
        Assertions.assertEquals(new ShortType(5).toString(), "5s");
        Assertions.assertEquals(new IntType(5).toString(), "5i");
        Assertions.assertEquals(new LongType(5).toString(), "5l");
        Assertions.assertEquals(new FloatType(5.5f).toString(), "5.5f");
        Assertions.assertEquals(new DoubleType(5.5).toString(), "5.5d");
        Assertions.assertEquals(new BigDecType(new BigDecimal("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890")).toString(), "1234567890123456789012345678901234567890.9876543210123456789012345678901234567890D");
        Assertions.assertEquals(new UUIDType(UUID.fromString("00000000-0000-0000-0000-000000000000")).toString(), "<00000000-0000-0000-0000-000000000000>");
        Assertions.assertEquals(new BitSetType(new BitSet()).toString(), "x;");
        BitSet obj = new BitSet(5);
        obj.set(0);
        obj.set(3);
        obj.set(4);
        obj.set(9);
        Assertions.assertEquals(new BitSetType(obj).toString(), "x1001100001;");

        Assertions.assertEquals(new IntType(5).copy(), new IntType(5));
        Assertions.assertEquals(new StringType("Apple").copy(), new StringType("Apple"));
        Assertions.assertEquals(new BooleanType(true).copy(), new BooleanType(true));
        Assertions.assertEquals(new ByteArrayType(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}).copy(), new ByteArrayType(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}));
        Assertions.assertEquals(new BigIntType(new BigInteger("1234567890123456789012345678901234567890")).copy(), new BigIntType(new BigInteger("1234567890123456789012345678901234567890")));
        Assertions.assertEquals(new ByteType(5).copy(), new ByteType(5));
        Assertions.assertEquals(new ShortType(5).copy(), new ShortType(5));
        Assertions.assertEquals(new IntType(5).copy(), new IntType(5));
        Assertions.assertEquals(new LongType(5).copy(), new LongType(5));
        Assertions.assertEquals(new FloatType(5.5f).copy(), new FloatType(5.5f));
        Assertions.assertEquals(new DoubleType(5.5).copy(), new DoubleType(5.5));
        Assertions.assertEquals(new BigDecType("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890").copy(), new BigDecType("1234567890123456789012345678901234567890.9876543210123456789012345678901234567890"));
        Assertions.assertEquals(new UUIDType(UUID.fromString("00000000-0000-0000-0000-000000000000")).copy(), new UUIDType(UUID.fromString("00000000-0000-0000-0000-000000000000")));
        Assertions.assertEquals(new BitSetType(new BitSet()).copy(), new BitSetType(new BitSet()));
        Assertions.assertEquals(new BitSetType(new BitSet(5)).copy(), new BitSetType(new BitSet(5)));
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
