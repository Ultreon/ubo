package dev.ultreon.tests.data;

import dev.ultreon.ubo.types.MapType;
import dev.ultreon.ubo.util.DataTypeVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
