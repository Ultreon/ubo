package com.ultreon.tests.data;

import com.ultreon.data.DataIo;
import com.ultreon.data.types.IType;
import com.ultreon.data.types.ListType;
import com.ultreon.data.types.MapType;
import com.ultreon.data.types.StringType;
import com.ultreon.data.util.DataTypeVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

class VisitiorTests {
    @Test
    @DisplayName("MapTypes")
    void deepCopy() {
        System.out.println("Deep copying map data type");
        MapType original = Utils.createExampleMap();

        MapType deepCopied = DataTypeVisitor.deepCopy(original);
        Assertions.assertEquals(original, deepCopied);
        Assertions.assertNotSame(original, deepCopied);
    }
}
