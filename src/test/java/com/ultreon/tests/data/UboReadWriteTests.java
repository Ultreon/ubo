package com.ultreon.tests.data;

import com.ultreon.data.DataIo;
import com.ultreon.data.types.ListType;
import com.ultreon.data.types.MapType;
import com.ultreon.data.types.StringType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class UboReadWriteTests {
    @Test
    @DisplayName("MapTypes")
    void readWriteMap() {
        MapType type = Utils.createExampleMap();

        try {
            System.out.println("Writing map data as normal UBO...");
            DataIo.write(type, new File("map-normal.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Writing map data as compressed UBO...");
            DataIo.writeCompressed(type, new File("map-compressed.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MapType readMap;
        try {
            System.out.println("Reading map data from normal UBO...");
            readMap = DataIo.read(new File("map-normal.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(readMap, type);

        MapType readCompressedMap;
        try {
            System.out.println("Reading map data from compressed UBO...");
            readCompressedMap = DataIo.readCompressed(new File("map-compressed.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(readCompressedMap, type);
    }

    @Test
    @DisplayName("ListTypes")
    void readWriteList() {
        ListType<StringType> list = new ListType<>();
        list.add(new StringType("Apple"));
        list.add(new StringType("Banana"));
        list.add(new StringType("Pear"));
        list.add(new StringType("Orange"));
        list.add(new StringType("Watermelon"));

        try {
            System.out.println("Writing normal list data...");
            DataIo.write(list, new File("list-normal.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Writing compressed list data...");
            DataIo.writeCompressed(list, new File("list-compressed.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ListType<StringType> readList;
        try {
            System.out.println("Reading normal list data...");
            readList = DataIo.read(new File("list-normal.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(readList, list);

        ListType<StringType> readCompressedList;
        try {
            System.out.println("Reading compressed list data...");
            readCompressedList = DataIo.readCompressed(new File("list-compressed.ubo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(readCompressedList, list);
    }
}
