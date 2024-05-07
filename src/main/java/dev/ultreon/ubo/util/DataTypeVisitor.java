package dev.ultreon.ubo.util;

import dev.ultreon.ubo.types.DataType;
import dev.ultreon.ubo.types.ListType;
import dev.ultreon.ubo.types.MapType;

import java.util.Map;

/**
 * A data type visitor. Useful for transforming data types into something else.
 *
 * @param <T> the type of the result of visiting the data type.
 * @author XyperCode
 */
public interface DataTypeVisitor<T> {
    DataTypeVisitor<String> WRITE_USO = DataType::writeUso;

    /**
     * Visits a data type.
     *
     * @param dataType the data type to visit.
     * @return the result of visiting the data type.
     */
    T visit(DataType<?> dataType);

    /**
     * Creates a data type visitor that deep copies the data type.
     *
     * @return the copied data type visitor.
     */
    static DataTypeVisitor<DataType<?>> deepCopy() {
        return type1 -> {
            if (type1 instanceof ListType<?>) {
                ListType<?> original = (ListType<?>) type1;
                ListType<DataType<?>> listType = new ListType<>(original.type());
                for (DataType<?> dataType : ((ListType<?>) type1).getValue()) {
                    listType.add(deepCopy(dataType));
                }

                return listType;
            }

            if (type1 instanceof MapType) {
                MapType mapType = new MapType();
                for (Map.Entry<String, DataType<?>> entry : ((MapType) type1).getValue().entrySet()) {
                    mapType.put(entry.getKey(), deepCopy(entry.getValue()));
                }

                return mapType;
            }

            return type1.copy();
        };
    }

    /**
     * Deep copies a data type.
     *
     * @param type the data type to copy.
     * @return the copied data type.
     * @param <T> the type of the data type.
     */
    @SuppressWarnings("unchecked")
    static <T extends DataType<?>> T deepCopy(T type) {
        return (T) type.accept(deepCopy());
    }
}
