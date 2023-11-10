package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Objects;

public class BitSetType implements IType<BitSet> {
    private BitSet obj;

    public BitSetType(byte[] bytes) {
        this.obj = BitSet.valueOf(bytes);
    }

    public BitSetType(BitSet obj) {
        this.obj = obj;
    }

    @Override
    public BitSet getValue() {
        return obj;
    }

    @Override
    public void setValue(BitSet obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.BIT_SET;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        byte[] arr = this.obj.toByteArray();
        if (arr.length >= 32768) throw new IllegalArgumentException("Bitset is too big to be written");
        stream.writeShort(arr.length);
        for (byte b : arr) {
            stream.writeByte(b);
        }
    }

    public static BitSetType read(DataInputStream stream) throws IOException {
        int len = stream.readShort();
        byte[] arr = new byte[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readByte();
        }
        return new BitSetType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BitSetType)) return false;
        BitSetType uuidType = (BitSetType) other;
        return Objects.equals(obj, uuidType.obj);
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }

    @Override
    public BitSetType copy() {
        return new BitSetType((BitSet) this.obj.clone());
    }
}
