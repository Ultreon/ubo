package com.ultreon.data.types;

import com.ultreon.data.TypeRegistry;
import com.ultreon.data.Types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ListType<T extends IType<?>> implements IType<List<T>>, Iterable<T> {
    private final int id;
    Class<?> componentType;
    private List<T> obj;

    public ListType(int id) {
        this(new ArrayList<>(), id);
    }

    @SafeVarargs
    public ListType(T... type) {
        this(type.getClass().getComponentType());
    }

    @SafeVarargs
    public ListType(List<T> obj, T... type) {
        this(obj, type.getClass().getComponentType());
    }

    private ListType(Class<?> type) {
        this(new ArrayList<>(), TypeRegistry.getIdOrThrow(type));
    }

    private ListType(List<T> obj, Class<?> type) {
        this.obj = obj;
        this.id = TypeRegistry.getIdOrThrow(type);
        this.componentType = type;
    }

    private ListType(List<T> list, int id) {
        setValue(list);
        this.id = id;
        this.componentType = TypeRegistry.getType(id);
    }

    @Override
    public List<T> getValue() {
        return obj;
    }

    @Override
    public void setValue(List<T> obj) {
        int id = -1;
        List<T> list = new ArrayList<>();
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
        return Types.LIST;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.writeByte(id);
        stream.writeInt(obj.size());
        for (IType<?> l : obj) {
            l.write(stream);
        }
    }

    public static ListType<?> read(DataInputStream stream) throws IOException {
        int id = stream.readUnsignedByte();
        int len = stream.readInt();
        List<IType<?>> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(TypeRegistry.read(id, stream));
        }

        return new ListType<>(list, id);
    }

    public void add(T type) {
        obj.add(type);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final List<T> list = new ArrayList<>(getValue());
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public T next() {
                if (index++ >= list.size())
                    throw new NoSuchElementException("No more elements in list");

                return list.get(index);
            }
        };
    }

    public int type() {
        return id;
    }

    @SafeVarargs
    public final <C extends IType<?>> ListType<C> cast(C... type) {
        return this.cast(type.getClass().getComponentType());
    }

    @SuppressWarnings("unchecked")
    final <C extends IType<?>> ListType<C> cast(Class<?> type) {
        ListType<C> cs = new ListType<>(type);
        cs.setValue((List<C>) obj);
        return cs;
    }

    public T get(int index) {
        return obj.get(index);
    }

    public boolean remove(int index) {
        return obj.remove(get(index));
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

    public int size() {
        return obj.size();
    }
}
