package dev.ultreon.tests.data;

import dev.ultreon.ubo.DataIo;
import dev.ultreon.ubo.types.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.BitSet;
import java.util.UUID;

class UsoReadWriteTests {
    @Test
    @DisplayName("MapTypes")
    void readWriteMap() throws IOException {
        MapType type = Utils.createExampleMap();

        String uso;
        System.out.println("Writing map data as USO...");
        uso = DataIo.toUso(type);

        Files.write(new File("map.uso").toPath(), uso.getBytes(StandardCharsets.UTF_8));

        MapType readMap;
        try {
            System.out.println("Reading map data from USO...");
            readMap = DataIo.fromUso(uso);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(type, readMap);
    }

    @Test
    @DisplayName("ListTypes")
    void readWriteList() throws IOException {
        ListType<StringType> list = new ListType<>();
        list.add(new StringType("Apple"));
        list.add(new StringType("Banana"));
        list.add(new StringType("Pear"));
        list.add(new StringType("Orange"));
        list.add(new StringType("Watermelon"));

        System.out.println("Writing normal list data...");
        String uso = DataIo.toUso(list);

        Files.write(new File("list.uso").toPath(), uso.getBytes(StandardCharsets.UTF_8));

        ListType<StringType> readList;
        try {
            System.out.println("Reading normal list data...");
            readList = DataIo.fromUso(uso);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(readList, list);
    }
    
    @Test
    @DisplayName("PrimitiveTypes")
    void readWritePrimitive() {
        Assertions.assertEquals("\"Apple\"", DataIo.toUso(new StringType("Apple")));
        Assertions.assertEquals("true", DataIo.toUso(new BooleanType(true)));
        Assertions.assertEquals("5b", DataIo.toUso(new ByteType(5)));
        Assertions.assertEquals("5s", DataIo.toUso(new ShortType(5)));
        Assertions.assertEquals("5i", DataIo.toUso(new IntType(5)));
        Assertions.assertEquals("5l", DataIo.toUso(new LongType(5)));
        Assertions.assertEquals("5.5f", DataIo.toUso(new FloatType(5.5f)));
        Assertions.assertEquals("5.5d", DataIo.toUso(new DoubleType(5.5)));
        Assertions.assertEquals("'a'", DataIo.toUso(new CharType('a')));
        Assertions.assertEquals("1I", DataIo.toUso(new BigIntType(BigInteger.ONE)));
        Assertions.assertEquals("1D", DataIo.toUso(new BigDecType(BigDecimal.ONE)));
        Assertions.assertEquals("<00000000-0000-0000-0000-000000000000>", DataIo.toUso(new UUIDType(UUID.fromString("00000000-0000-0000-0000-000000000000"))));
        Assertions.assertEquals("x;", DataIo.toUso(new BitSetType(new BitSet())));
        Assertions.assertEquals("[\"Apple\"]", DataIo.toUso(new ListType<>(new StringType("Apple"))));
        Assertions.assertEquals("{}", DataIo.toUso(new MapType()));
    }
}
