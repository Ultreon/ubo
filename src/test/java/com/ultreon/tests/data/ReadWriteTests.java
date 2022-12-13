package com.ultreon.tests.data;

import com.ultreon.data.DataIo;
import com.ultreon.data.Types;
import com.ultreon.data.types.ListType;
import com.ultreon.data.types.MapType;
import com.ultreon.data.types.StringType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ReadWriteTests {
    @Test
    @DisplayName("Write -> Map")
    void writeMap() {
        MapType type = new MapType();
        type.putInt("integer", 123456789);
        type.putString("string", "Hello World");
        type.putByte("byte", 64);
        type.putShort("short", 1024);
        type.putUUID("uuid", UUID.nameUUIDFromBytes("Hello World".getBytes()));
        type.putDouble("double", 123456.123456789);
        type.putFloat("float", 123.456f);
        type.putLong("long", 7342041283402173783L);
        type.putLongArray("longArray", new long[]{393784924237483L, 485934279453232L, 78523942493L, 763828943274932L, 7534792734329L, 578432640347329432L});
        type.putIntArray("integerArray", new int[]{970423324,324354353,546345436,345436345,56,654354354,353454354,54352543,364335,545});
        type.putShortArray("shortArray", new short[]{9234,433,5453,20495,94,5385,38,9843,572,34,729,4,485,3986,2964,64,5843,20275,3821,14573,3584,995});
        type.putByteArray("byteArray", new byte[]{-32,109,-4,65,-74,111,35,95,-10,-104,59,21,-73,87,-49,79,63,-90,121,103,1});
        type.putChar("character", '#');
        type.putChar("unicodeSymbol", '\u263A');

        MapType inner = new MapType();
        inner.putString("message", "Hello, Glitch.");
        inner.putString("name", "Glitch");
        inner.putInt("highScore", 7638);
        inner.putLong("fileSize", 7_323_358_494L);
        type.put("Map", inner);

        ListType list = new ListType(Types.STRING);
        list.add(new StringType("Glitch"));
        list.add(new StringType("Qboi"));
        list.add(new StringType("QTechCommunity"));
        list.add(new StringType("UltreonTeam"));
        type.put("List", list);

        try {
            System.out.println("Writing normal map data...");
            DataIo.write(type, new File("map-normal.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Writing compressed map data...");
            DataIo.writeCompressed(type, new File("map-compressed.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Read -> Map")
    void readMap() {
        try {
            System.out.println("Reading normal map data...");
            MapType map = DataIo.read(new File("map-normal.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Reading compressed map data...");
            MapType map = DataIo.readCompressed(new File("map-compressed.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
