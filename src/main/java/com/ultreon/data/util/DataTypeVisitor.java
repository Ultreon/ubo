package com.ultreon.data.util;

import com.ultreon.data.types.IType;
import com.ultreon.data.types.ListType;
import com.ultreon.data.types.MapType;

import java.util.Map;

/**
 * A data type visitor. Useful for transforming data types into something else.
 *
 * @param <T> the type of the result of visiting the data type.
 * @author XyperCode
 */
public interface DataTypeVisitor<T> {
    DataTypeVisitor<String> WRITE_USO = IType::writeUso;

    /**
     * Visits a data type.
     *
     * @param type the data type to visit.
     * @return the result of visiting the data type.
     */
    T visit(IType<?> type);

    /**
     * Creates a data type visitor that deep copies the data type.
     *
     * @return the copied data type visitor.
     */
    static DataTypeVisitor<IType<?>> deepCopy() {
        return type1 -> {
            if (type1 instanceof ListType<?>) {
                ListType<?> original = (ListType<?>) type1;
                ListType<IType<?>> listType = new ListType<>(original.type());
                for (IType<?> iType : ((ListType<?>) type1).getValue()) {
                    listType.add(deepCopy(iType));
                }

                return listType;
            }

            if (type1 instanceof MapType) {
                MapType mapType = new MapType();
                for (Map.Entry<String, IType<?>> entry : ((MapType) type1).getValue().entrySet()) {
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
    static <T extends IType<?>> T deepCopy(T type) {
        return (T) type.accept(deepCopy());
    }
}
