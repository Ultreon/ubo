package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypeRegistry;
import dev.ultreon.ubo.DataTypes;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListType<T extends DataType<?>> implements DataType<List<T>>, Iterable<T> {
    private final int id;
    Class<?> componentType;
    private List<T> obj;

    public ListType(int id) {
        this(new ArrayList<>(), id);
    }

    @SafeVarargs
    public ListType(T... type) {
        this(type.getClass().getComponentType());

        this.setValue(Arrays.asList(type));
    }

    @SafeVarargs
    public ListType(List<T> obj, T... type) {
        this(obj, type.getClass().getComponentType());
    }

    private ListType(Class<?> type) {
        this(new ArrayList<>(), DataTypeRegistry.getIdOrThrow(type));
    }

    private ListType(List<T> obj, Class<?> type) {
        this.obj = obj;
        this.id = DataTypeRegistry.getIdOrThrow(type);
        this.componentType = type;
    }

    private ListType(List<T> list, int id) {
        setValue(list);
        this.id = id;
        this.componentType = DataTypeRegistry.getType(id);
    }

    @Override
    public List<T> getValue() {
        return obj;
    }

    @Override
    public void setValue(List<T> obj) {
        int id = -1;
        List<T> list = new ArrayList<>(obj.size());
        for (int i = 0, objSize = obj.size(); i < objSize; i++) {
            T iType = obj.get(i);
            if (id == -1) {
                id = iType.id();
            }
            if (id != iType.id()) {
                throw new IllegalArgumentException("Type at index " + i + " has invalid id: " + iType.id() + " (expected " + id + ")");
            }

            list.add(iType);
        }

        this.obj = list;
    }

    @Override
    public int id() {
        return DataTypes.LIST;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(id);
        output.writeInt(obj.size());
        for (DataType<?> l : obj) {
            l.write(output);
        }
    }

    public static ListType<?> read(DataInput input) throws IOException {
        int id = input.readUnsignedByte();
        int len = input.readInt();
        List<DataType<?>> list = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            list.add(DataTypeRegistry.read(id, input));
        }

        return new ListType<>(list, id);
    }

    public void add(T type) {
        if (type.id() != id)
            throw new IllegalArgumentException("Type has invalid id: " + type.id() + " (expected " + id + ")");
        obj.add(type);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return obj.iterator();
    }

    public @NotNull ListIterator<T> listIterator() {
        return obj.listIterator();
    }

    public int type() {
        return id;
    }

    @SafeVarargs
    public final <C extends DataType<?>> ListType<C> cast(C... type) {
        return this.cast(type.getClass().getComponentType());
    }

    @SuppressWarnings("unchecked")
    final <C extends DataType<?>> ListType<C> cast(Class<?> type) {
        ListType<C> cs = new ListType<>(type);
        cs.setValue((List<C>) obj);
        return cs;
    }

    public T get(int index) {
        return obj.get(index);
    }

    public boolean remove(int index) {
        if (index >= obj.size())
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        if (index < 0)
            index = obj.size() + index;
        if (index < 0)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        obj.remove(index);
        return true;
    }

    public T pop(int index) {
        return obj.remove(index);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ListType)) return false;
        ListType<?> listType = (ListType<?>) other;
        return id == listType.id && Objects.equals(obj, listType.obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListType<T> copy() {
        return new ListType<>(obj.stream().map(t -> (T) t.copy()).collect(Collectors.toList()));
    }

    @Override
    public String writeUso() {
        StringBuilder builder = new StringBuilder("[");
        for (T t : this)
            builder.append(t.writeUso()).append(", ");

        if (this.obj.size() > 0)
            return builder.substring(0, builder.length() - 2) + "]";

        return builder + "]";
    }

    public int size() {
        return obj.size();
    }

    public void clear() {
        obj.clear();
    }

    public T set(int index, T type) {
        if (type.id() != id)
            throw new IllegalArgumentException("Type at index " + index + " has invalid id: " + type.id() + " (expected " + id + ")");
        return obj.set(index, type);
    }

    public T remove(T type) {
        obj.remove(type);
        return type;
    }

    public boolean contains(T type) {
        return obj.contains(type);
    }

    public <R> List<R> mapTo(Function<T, R> mapper) {
        return mapTo(mapper, new ArrayList<>());
    }

    public <R> List<R> mapTo(Function<T, R> mapper, List<R> list) {
        for (T t : obj) {
            R r = mapper.apply(t);
            list.add(r);
        }
        return list;
    }

    public Stream<T> stream() {
        return obj.stream();
    }

    public boolean isEmpty() {
        return obj.isEmpty();
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
