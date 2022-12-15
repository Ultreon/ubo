package com.ultreon.data.types;

import com.ultreon.data.TypeRegistry;
import com.ultreon.data.Types;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListType implements IType<List<IType<?>>>, Iterable<IType<?>> {
    private List<IType<?>> obj;
    private int id;

    public ListType(int id) {
        this(new ArrayList<>(), id);
    }

    public ListType(List<IType<?>> obj, int id) {
        this.obj = obj;
        this.id = id;
    }

    private ListType(List<IType<?>> list) {
        setValue(list);
    }

    @Override
    public List<IType<?>> getValue() {
        return obj;
    }

    @Override
    public void setValue(List<IType<?>> obj) {
        int id = -1;
        List<IType<?>> list = new ArrayList<>();
        for (int i = 0, objSize = obj.size(); i < objSize; i++) {
            IType<?> iType = obj.get(i);
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

    public static ListType read(DataInputStream stream) throws IOException {
        var id = stream.readByte();
        int len = stream.readInt();
        List<IType<?>> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(TypeRegistry.read(id, stream));
        }
        
        return new ListType(list);
    }

    public void add(IType<?> type) {
        obj.add(type);
    }

    @Override
    public Iterator<IType<?>> iterator() {
        return new Iterator<>() {
            private final List<IType<?>> list = new ArrayList<>(getValue());
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public IType<?> next() {
                return list.get(index++);
            }
        };
    }

    public int type() {
        return id;
    }
}
