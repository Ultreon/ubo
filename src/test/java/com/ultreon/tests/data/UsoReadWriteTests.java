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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
}
